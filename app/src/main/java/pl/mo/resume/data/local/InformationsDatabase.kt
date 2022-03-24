package pl.mo.resume.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.mo.resume.data.model.ChildInformationConverters
import pl.mo.resume.data.model.ParentInformation

@Database(
    entities = [ParentInformation::class],
    version = DatabaseMigrations.DB_VERSION
)
@TypeConverters(ChildInformationConverters::class)
abstract class InformationsDatabase : RoomDatabase() {

    /**
     * @return [ParentInformationDao] Resume Informations Data Access Object.
     */
    abstract fun getInformationsDao(): ParentInformationDao

    companion object {
        private const val DB_NAME = "resume_database"

        @Volatile
        private var INSTANCE: InformationsDatabase? = null

        fun getInstance(context: Context): InformationsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InformationsDatabase::class.java,
                    DB_NAME
                ).addMigrations(*DatabaseMigrations.MIGRATIONS).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}