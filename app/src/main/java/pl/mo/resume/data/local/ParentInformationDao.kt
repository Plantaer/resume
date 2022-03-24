package pl.mo.resume.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.mo.resume.data.model.ParentInformation

@Dao
interface ParentInformationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInformations(informations: List<ParentInformation>)

    @Query("DELETE FROM ${ParentInformation.TABLE_NAME}")
    suspend fun deleteAllInformations()

    @Query("SELECT * FROM ${ParentInformation.TABLE_NAME} WHERE ID = :informationId")
    fun getInformationById(informationId: Int): Flow<ParentInformation>

    @Query("SELECT * FROM ${ParentInformation.TABLE_NAME}")
    fun getAllInformations(): Flow<List<ParentInformation>>
}