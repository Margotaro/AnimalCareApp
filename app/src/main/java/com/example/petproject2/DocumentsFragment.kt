package com.example.petproject2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.petproject2.database.AppDatabase
import com.example.petproject2.database.Document
import kotlinx.android.synthetic.main.activity_home_page.*

private const val ARG_PET_ID = "PetId"
private const val DOCUMENT_SELECT =0

class DocumentsFragment : Fragment(), PetScenarioSliderFragment {
    private var petId: Int? = null
    lateinit var gvDocuments: GridView
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getInt(ARG_PET_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_documents, container, false)
        gvDocuments = view.findViewById(R.id.documentTableLayout)
        loadFromDatabase()
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun showContent(petId: Int) {

    }

    override fun getFragmentObject(): Fragment {
        return this
    }
    fun loadFromDatabase() {

        context?.let { context ->
            database = AppDatabase.getDatabase(context)

            val docList = listOf(
                Document(
                    "pretty cat photo", "image", AppDatabase.getPetUriStringFromDrawable(
                        "pet_placeholder2",
                        context
                    )
                ),
                Document(
                    "pretty cat passport", "pdf", AppDatabase.getPetUriStringFromDrawable(
                        "pet_placeholder2",
                        context
                    )
                ),
                Document(
                    "pretty dog vaccine pass", "pdf", AppDatabase.getPetUriStringFromDrawable(
                        "pet_placeholder2",
                        context
                    )
                )
            )

            val adapter = DocumentListAdapter(context, R.layout.pet_page_document_icon, docList)

            gvDocuments.adapter = adapter

            gvDocuments.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->

                if(position >= docList.size)
                {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "*/*"
                    startActivityForResult(intent, DOCUMENT_SELECT)
                    return@OnItemClickListener
                }

                //val intent = Intent(this, PetPageActivity::class.java) open
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == DOCUMENT_SELECT) {
            var uri = data?.data
            /*database.let {
                if(petId == null) return@let
                val notificationEntityList = it.documentDao().findDocumentList(petId!!)?.bar
                val docList = notificationEntityList?.map{ docEntity -> Document(docEntity) }?.toMutableList()
                docList?.let {
                    RefreshDocumentListView()
                    }
                }*/
            }
        }

    fun RefreshDocumentListView() {

    }

    companion object {
            @JvmStatic
            fun newInstance(petId: Int) =
                DocumentsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PET_ID, petId)
                    }
                }
        }
}

