package com.example.geofencing.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.geofencing.model.MyGeofence;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class GeofenceDAO_Impl implements GeofenceDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MyGeofence> __insertionAdapterOfMyGeofence;

  private final EntityDeletionOrUpdateAdapter<MyGeofence> __deletionAdapterOfMyGeofence;

  private final EntityDeletionOrUpdateAdapter<MyGeofence> __updateAdapterOfMyGeofence;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public GeofenceDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMyGeofence = new EntityInsertionAdapter<MyGeofence>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `table_geofencing` (`geofenceId`,`Area name`,`Latitude area`,`Longitude area`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyGeofence value) {
        if (value.getGeofenceId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getGeofenceId());
        }
        if (value.getAreaName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAreaName());
        }
        stmt.bindDouble(3, value.getLatitude());
        stmt.bindDouble(4, value.getLongitude());
      }
    };
    this.__deletionAdapterOfMyGeofence = new EntityDeletionOrUpdateAdapter<MyGeofence>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `table_geofencing` WHERE `geofenceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyGeofence value) {
        if (value.getGeofenceId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getGeofenceId());
        }
      }
    };
    this.__updateAdapterOfMyGeofence = new EntityDeletionOrUpdateAdapter<MyGeofence>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `table_geofencing` SET `geofenceId` = ?,`Area name` = ?,`Latitude area` = ?,`Longitude area` = ? WHERE `geofenceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyGeofence value) {
        if (value.getGeofenceId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getGeofenceId());
        }
        if (value.getAreaName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAreaName());
        }
        stmt.bindDouble(3, value.getLatitude());
        stmt.bindDouble(4, value.getLongitude());
        if (value.getGeofenceId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getGeofenceId());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM table_geofencing";
        return _query;
      }
    };
  }

  @Override
  public long insert(final MyGeofence myGeofence) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMyGeofence.insertAndReturnId(myGeofence);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final MyGeofence... geofence) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMyGeofence.insert(geofence);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final MyGeofence myGeofence) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMyGeofence.handle(myGeofence);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final MyGeofence myGeofence) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMyGeofence.handle(myGeofence);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<Long> getCount() {
    final String _sql = "SELECT COUNT(*) FROM table_geofencing";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"table_geofencing"}, false, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if(_cursor.moveToFirst()) {
            final long _tmp;
            _tmp = _cursor.getLong(0);
            _result = _tmp;
          } else {
            _result = 0L;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MyGeofence>> getAllGeofences() {
    final String _sql = "SELECT * FROM table_geofencing";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"table_geofencing"}, false, new Callable<List<MyGeofence>>() {
      @Override
      public List<MyGeofence> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfGeofenceId = CursorUtil.getColumnIndexOrThrow(_cursor, "geofenceId");
          final int _cursorIndexOfAreaName = CursorUtil.getColumnIndexOrThrow(_cursor, "Area name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "Latitude area");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "Longitude area");
          final List<MyGeofence> _result = new ArrayList<MyGeofence>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MyGeofence _item;
            final Long _tmpGeofenceId;
            if (_cursor.isNull(_cursorIndexOfGeofenceId)) {
              _tmpGeofenceId = null;
            } else {
              _tmpGeofenceId = _cursor.getLong(_cursorIndexOfGeofenceId);
            }
            final String _tmpAreaName;
            if (_cursor.isNull(_cursorIndexOfAreaName)) {
              _tmpAreaName = null;
            } else {
              _tmpAreaName = _cursor.getString(_cursorIndexOfAreaName);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item = new MyGeofence(_tmpGeofenceId,_tmpAreaName,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
