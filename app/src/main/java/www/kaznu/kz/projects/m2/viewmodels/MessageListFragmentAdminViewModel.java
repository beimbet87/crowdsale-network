package www.kaznu.kz.projects.m2.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;
import www.kaznu.kz.projects.m2.repositories.MessageListFragmentAdminRepository;

public class MessageListFragmentAdminViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<MessageList>> mMessageList;
    private final CurrentUser currentUser;
    private MessageListFragmentAdminRepository mMessageListFragmentAdminRepository;

    public MessageListFragmentAdminViewModel(@NonNull Application application) {
        super(application);
        currentUser = new CurrentUser(application.getApplicationContext());
    }

    public void init() {
        if (mMessageList != null) {
            return;
        }

        mMessageListFragmentAdminRepository = MessageListFragmentAdminRepository.getInstance();
        mMessageList = mMessageListFragmentAdminRepository.getMessageList(currentUser);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mMessageListFragmentAdminRepository.clearData();
    }

    public LiveData<ArrayList<MessageList>> getMessageList() {
        return mMessageList;
    }
}
