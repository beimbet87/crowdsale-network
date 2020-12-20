package www.kaznu.kz.projects.m2.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.interfaces.IMessageList;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;

public class MessageListFragmentRepository implements Constants, IMessageList {

    private static MessageListFragmentRepository instance;
    private final ArrayList<MessageList> dataSet = new ArrayList<>();

    public static MessageListFragmentRepository getInstance() {
        if(instance == null) {
            instance = new MessageListFragmentRepository();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<MessageList>> getMessageList(CurrentUser user) {
        setMessageList(user);
        MutableLiveData<ArrayList<MessageList>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        return data;
    }

    private void setMessageList(CurrentUser user) {
        ArrayList<Chat> data = user.getClientMessageList();

        for (int i = 0; i < data.size(); i++) {
            String image = BASE_URL + data.get(i).getImageLink();
            String title = data.get(i).getCompanyName();
            String lastMessage = data.get(i).getLastMessage();
            Integer messageCount = data.get(i).getCount();
            Integer company = data.get(i).getCompany();
            Integer refRealty = data.get(i).getRefRealty();

            dataSet.add(new MessageList(image, title, lastMessage, messageCount, company, refRealty));
        }
    }

    public void clearData() {
        dataSet.clear();
    }
}
