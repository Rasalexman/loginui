package com.mincor.login.mediators.login

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.mincor.flairframework.interfaces.activity
import com.mincor.flairframework.interfaces.resources
import com.mincor.flairframework.interfaces.stringRes
import com.mincor.login.R
import com.mincor.login.common.extensions.*
import com.mincor.login.mediators.base.BaseToolbarMediator
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onItemSelectedListener


class MainMediator : BaseToolbarMediator() {

    override fun createLayout(context: Context): View = MainUI().createView(AnkoContext.create(context, this))

    override var title: String = ""
        get() = stringRes(R.string.mainScreenTF)

    private var selectedValueTV:TextView? = null
    private var selectorSP:Spinner? = null

    override fun onCreatedView(view: View) {
        super.onCreatedView(view)
        hasOptionalMenu = true
        setHomeButtonEnable()
        //val adapter = ArrayAdapter.createFromResource(activity,R.array.main_drops, android.R.layout.simple_spinner_item)
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //selectorSP?.adapter = adapter
    }

    override fun onDestroyView() {
        selectedValueTV = null
        selectorSP?.adapter = null
        selectorSP = null
        super.onDestroyView()
    }

    inner class CustomArrayAdapter(private val cnt: Context? = null,
                                   private val objects: List<String>) : ArrayAdapter<String>(cnt, -1, objects) {

        override fun getDropDownView(position: Int, convertView: View?,
                                     parent: ViewGroup): View = getCustomView(position, convertView, parent)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View = getCustomView(position, convertView, parent)

        fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
            val row = convertView?:CustomSpinnerUI().createView(AnkoContext.create(cnt!!, this))
            val label = row.findViewById(R.id.item_id) as TextView
            label.text = objects[position]
            return row
        }
    }

    inner class CustomSpinnerUI : AnkoComponent<CustomArrayAdapter> {
        override fun createView(ui: AnkoContext<CustomArrayAdapter>): View = with(ui) {
            linearLayout {
                textView {
                    id = R.id.item_id
                    textColor = Color.BLACK
                    textSize = 16f
                    padding = dip8()
                }
            }
        }
    }


    inner class MainUI : AnkoComponent<MainMediator> {
        override fun createView(ui: AnkoContext<MainMediator>): View = with(ui) {
            verticalLayout {
                lparams(matchParent, matchParent)
                gravity = Gravity.CENTER_HORIZONTAL

                selectedValueTV = textView {
                    textSize = 16f
                    textColor = Color.WHITE
                    background = roundedBg(Color.LTGRAY)
                    padding = dip8()
                    gravity = Gravity.CENTER
                }.lparams(wdthProc(0.6f), hdthProc(0.08f)) {
                    margin = dip16()
                }

                selectorSP = spinner {
                    background = roundedBg(color(R.color.colorPurpleLight))
                    adapter = CustomArrayAdapter(activity, resources().getStringArray(R.array.main_drops).toList())

                    onItemSelectedListener {
                        this.onItemSelected { adapterView, view, i, l ->
                            selectedValueTV?.text = adapterView?.getItemAtPosition(i).toString()
                        }
                    }
                    padding = dip8()
                }.lparams(wdthProc(0.6f))

            }
        }
    }
}