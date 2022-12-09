package com.example.tugasbesarpsi.`object`

import android.net.Uri
import android.provider.ContactsContract.Data
import com.example.tugasbesarpsi.`class`.Doctor
import com.example.tugasbesarpsi.`class`.Hospital
import com.example.tugasbesarpsi.`class`.Person
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.UUID

object DAO {
    private val db = FirebaseDatabase.getInstance("https://tugas-besar-psi-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val db2 = FirebaseStorage.getInstance("gs://tugas-besar-psi.appspot.com")
    private val databaseReferencePerson: DatabaseReference = db.getReference("Person")
    private val databaseReferenceHospital: DatabaseReference = db.getReference("Hospital")
    private val databaseReferenceHistory: DatabaseReference = db.getReference("History")
    private val storageReference = db2.getReference("Hospital")
    var ref = storageReference.child("images/")

    fun addPerson(person: Person, idPerson: String): Task<Void> {
        return databaseReferencePerson.child(idPerson).setValue(person)
    }

    fun getPerson(): Query {
        return databaseReferencePerson.orderByKey()
    }

    fun getSpecificPerson(id: String): Query {
        return db.getReference("Person/$id").orderByKey()
    }

    fun addHospital(hospital: Hospital, idHospital: String): Task<Void> {
        return databaseReferenceHospital.child(idHospital).setValue(hospital)
    }

    fun deleteHospital(idHospital: String): Task<Void> {
        return databaseReferenceHospital.child(idHospital).removeValue()
    }

    fun updateHospital(idHospital: String, hashMap: HashMap<String, Any>): Task<Void> {
        return databaseReferenceHospital.child(idHospital).updateChildren(hashMap)
    }

    fun getHospital(): Query {
        return databaseReferenceHospital.orderByKey()
    }

    fun addHistory(doctor: Doctor, idPerson: String): Task<Void> {
        val id = UUID.randomUUID()
        return databaseReferenceHistory.child(idPerson).child(id.toString()).setValue(doctor)
    }
    fun getHistory(id: String): Query {
        return db.getReference("History/$id").orderByKey()
    }

    fun uploadImage(path: Uri, id: String): UploadTask {
        ref = storageReference.child("images/$id")
        return ref.putFile(path)
    }
}