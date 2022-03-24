import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.mo.resume.data.local.InformationsDatabase
import pl.mo.resume.data.model.ParentInformation

@RunWith(AndroidJUnit4::class)
class ParentInformationDaoTest {

    private lateinit var mDatabase: InformationsDatabase
    private val data = listOf(
        ParentInformation(1, "Test 1", "Test 1", "Test 1"),
        ParentInformation(2, "Test 2", "Test 2", "Test 2")
    )

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            InformationsDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_get_all_informations() = runBlocking {
        mDatabase.getInformationsDao().addInformations(data)

        val dataFromDatabase = mDatabase.getInformationsDao().getAllInformations().first()

        assertThat(dataFromDatabase, equalTo(data))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_get_information_by_id() = runBlocking {

        mDatabase.getInformationsDao().addInformations(data)

        var dataItem = mDatabase.getInformationsDao().getInformationById(1).first()
        assertThat(dataItem, equalTo(data[0]))

        dataItem = mDatabase.getInformationsDao().getInformationById(2).first()
        assertThat(dataItem, equalTo(data[1]))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}
