package yummylau.feature.repository;

import android.arch.lifecycle.MutableLiveData;

import yummylau.feature.repository.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/5.
 */

public class MainRepository {

    private RemoteRepository mRemoteRepository = new RemoteRepository();
    private LocalRepository mLocalRepository = new LocalRepository();

    public MutableLiveData<UserEntity> getOwnInfo() {

        return new MutableLiveData<>();
    }

    class RemoteRepository {

    }

    class LocalRepository {

    }
}
