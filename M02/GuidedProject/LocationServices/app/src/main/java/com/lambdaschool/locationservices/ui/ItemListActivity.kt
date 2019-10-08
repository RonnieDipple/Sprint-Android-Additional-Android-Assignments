package com.lambdaschool.locationservices.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.lambdaschool.locationservices.R
import com.lambdaschool.locationservices.arch.Injection
import com.lambdaschool.locationservices.model.Contact
import com.lambdaschool.locationservices.model.ContactResults
import com.lambdaschool.locationservices.retrofit.JsonPlaceHolderApi
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [.ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var itemListViewModel: ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        itemListViewModel = ViewModelProviders
            .of(this, Injection.provideViewModelFactory())
            .get(ItemListViewModel::class.java)


        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, twoPane)

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val twoPane: Boolean
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
        private val values: MutableList<Contact>

        var jsonPlaceHolderApi: JsonPlaceHolderApi = JsonPlaceHolderApi.Factory.create()

        private val itemOnClickListener = View.OnClickListener { view ->
            val item = view.tag as Contact
            if (twoPane) {

                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        // TODO: S09M02-8a Use Serializable to pass the Contact
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()

            } else {
                val intent = Intent(view.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item)
                }
                view.context.startActivity(intent)
            }
        }

        init {
            values = ArrayList()
            getContacts(CONTACTS_SIZE)
        }

        fun refetchContacts() {
            parentActivity.itemListViewModel.clearSavedContacts()
            getContacts(CONTACTS_SIZE)
        }

        private fun getContacts(resultSize: Int) {
            if (parentActivity.itemListViewModel.savedContactsSize() > 0) {
                updateContactsList()
                return
            }
            parentActivity.progressBar.visibility = View.VISIBLE
            jsonPlaceHolderApi.getContacts(results = resultSize)
                .enqueue(object : Callback<ContactResults> {

                    override fun onFailure(call: Call<ContactResults>, throwable: Throwable) {
                        parentActivity.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            parentActivity,
                            "Can't retrieve contacts",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ContactResults>,
                        response: Response<ContactResults>
                    ) {
                        parentActivity.progressBar.visibility = View.INVISIBLE

                        if (!response.isSuccessful) {
                            Toast.makeText(
                                parentActivity,
                                "Can't retrieve contacts (code: ${response.code()})",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val contactResults = response.body()
                            contactResults?.results?.let { contacts ->
                                parentActivity.itemListViewModel.putSavedContacts(contacts)
                                updateContactsList()
                            }
                        }
                    }
                })
        }

        private fun updateContactsList() {
            values.clear()
            values.addAll(parentActivity.itemListViewModel.getSavedContacts())
            parentActivity.runOnUiThread { notifyDataSetChanged() }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val contact = values[position]
            holder.idView.text = contact.getFullName()
            holder.contentView.text = contact.phone

            holder.imageView.load(contact.picture.medium) {
                crossfade(false)
                placeholder(R.drawable.noimage)
            }

            with(holder.itemView) {
                tag = contact
                setOnClickListener(itemOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
            val imageView: ImageView = view.image
        }

        companion object {
            const val CONTACTS_SIZE = 500
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.refresh_list -> {
                (item_list.adapter as SimpleItemRecyclerViewAdapter).refetchContacts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
