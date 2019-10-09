package com.lambdaschool.starwarsapitransitionsdemo

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class ItemDetailFragment : Fragment() {

    private var mItem: SwApiObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments!!.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            // S04M03-19 pull the object from the arguments
            mItem = arguments!!.getSerializable(ARG_ITEM_ID) as SwApiObject
            val activity = this.activity
            val appBarLayout = activity!!.findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
            /*appBarLayout.setBackground(getContext().getDrawable(
                    DrawableResolver.getDrawableId(
                            mItem.getCategory(),
                            mItem.getId())));*/
            if (mItem!!.category == DrawableResolver.CHARACTER) {
                appBarLayout.title = mItem!!.name
            } else if (mItem!!.category == DrawableResolver.STARSHIP) {
                appBarLayout.title = (mItem as Starship).costInCredits
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            (rootView.findViewById<View>(R.id.item_image) as ImageView).setImageDrawable(
                    rootView.context.getDrawable(
                            DrawableResolver.getDrawableId(
                                    it.category ?: "",
                                    it.id)))

            (rootView.findViewById<View>(R.id.item_text) as TextView).text = mItem!!.toString()
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the mItem ID that this fragment
         * represents.
         */
        val ARG_ITEM_ID = "item_id"
    }
}
