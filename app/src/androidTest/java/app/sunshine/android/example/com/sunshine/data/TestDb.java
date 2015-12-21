package app.sunshine.android.example.com.sunshine.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;


public class TestDb extends AndroidTestCase {

    @Override
    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(WeatherContract.LocationEntry.TABLE_NAME);
        tableNameHashSet.add(WeatherContract.WeatherEntry.TABLE_NAME);

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + WeatherContract.LocationEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(WeatherContract.LocationEntry._ID);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_CITY_NAME);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LAT);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LONG);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                locationColumnHashSet.isEmpty());

        c.close();
        db.close();
    }

    public void testLocationTable() {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues location = TestUtilities.createNorthPoleLocationValues();
        insertLocation(db, location);

        Cursor c = db.query(
                WeatherContract.LocationEntry.TABLE_NAME, null, null, null, null, null, null
        );
        assertTrue("Location not found.", c.moveToFirst());

        TestUtilities.validateCurrentRecord(
                "Values in the db do not match the specified on the insert.",
                c, location);

        assertFalse("More then one location row was returned.", c.moveToNext());

        c.close();
        db.close();
    }

    public void testWeatherTable() {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues location = TestUtilities.createNorthPoleLocationValues();
        long locationId = insertLocation(db, location);

        ContentValues weather =  TestUtilities.createWeatherValues(locationId);
        long weatherId = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, weather);
        assertTrue("Failure to insert weather values.", weatherId != -1);

        Cursor c = db.query(
                WeatherContract.WeatherEntry.TABLE_NAME, null, null, null, null, null, null
        );
        assertTrue("Weather not found.", c.moveToFirst());

        TestUtilities.validateCurrentRecord("Values in the db do not match the specified on the insert.",
                c, weather);

        assertFalse("More then one weather row was returned.", c.moveToNext());

        c.close();
        db.close();
    }

    private void deleteTheDatabase() {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
    }

    private SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = new WeatherDbHelper(this.mContext).getWritableDatabase();
        assertTrue(db.isOpen());
        return db;
    }

    private long insertLocation(SQLiteDatabase db, ContentValues location) {
        long locationId = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, location);
        assertTrue("Failure to insert location values.", locationId != -1);
        return locationId;
    }
}
