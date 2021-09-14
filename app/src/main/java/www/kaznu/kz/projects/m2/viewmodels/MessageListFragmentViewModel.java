package www.kaznu.kz.projects.m2.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;
import www.kaznu.kz.projects.m2.repositories.MessageListFragmentRepository;

public class MessageListFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<MessageList>> mMessageList;
    private MessageListFragmentRepository mMessageListFragmentRepository;

    public MessageListFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(CurrentUser currentUser) {
        if (mMessageList != null) {
            return;
        }

        mMessageListFragmentRepository = MessageListFragmentRepository.getInstance();
        mMessageList = mMessageListFragmentRepository.getMessageList(currentUser);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mMessageListFragmentRepository.clearData();
    }

    public LiveData<ArrayList<MessageList>> getMessageList() {
        return mMessageList;
    }
}
