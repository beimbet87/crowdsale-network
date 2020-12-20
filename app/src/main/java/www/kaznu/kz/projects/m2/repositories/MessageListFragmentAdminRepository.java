package www.kaznu.kz.projects.m2.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import www.kaznu.kz.projects.m2.interfaces.Constants;
import www.kaznu.kz.projects.m2.interfaces.IMessageList;
import www.kaznu.kz.projects.m2.models.Chat;
import www.kaznu.kz.projects.m2.models.CurrentUser;
import www.kaznu.kz.projects.m2.models.MessageList;

public class MessageListFragmentAdminRepository implements Constants, IMessageList {

    private static MessageListFragmentAdminRepository instance;
    private final ArrayList<MessageList> dataSet = new ArrayList<>();

    public static MessageListFragmentAdminRepository getInstance() {
        if(instance == null) {
            instance = new MessageListFragmentAdminRepository();
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
        ArrayList<Chat> data = user.getOwnerMessageList();

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
