package uk.laxd.androiddocker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import org.springframework.util.StringUtils;

import uk.laxd.androiddocker.dto.DockerVersion;

/**
 * Created by lawrence on 06/01/17.
 */

public class DockerDao extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Docker";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE = "DOCKER";

    private static final String DOCKER_ADDRESS = "ADDRESS";

    private static DockerDao instance;

    public static synchronized DockerDao getInstance(Context context) {
        if(instance == null) {
            instance = new DockerDao(context.getApplicationContext());
        }

        return instance;
    }

    private DockerDao(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE = "CREATE TABLE " + TABLE +
                "(" +
                    DOCKER_ADDRESS + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean isSetup() {
        return !TextUtils.isEmpty(getDockerAddress());
    }

    public void setDockerAddress(String dockerAddress) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DOCKER_ADDRESS, dockerAddress);

        // Purge previous addresses
        db.delete(TABLE, null, null);

        // Add our new address
        db.insert(TABLE, null, contentValues);
    }

    public String getDockerAddress() {
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.query(TABLE, new String[]{DOCKER_ADDRESS}, null, null, null, null, null)) {
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(DOCKER_ADDRESS));
            }
        } catch (Exception ignored) {
        }

        return null;
    }
}
