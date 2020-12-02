package www.kaznu.kz.projects.m2.interfaces;

public interface Constants {
    String TAG = "M2TAG";
    String BASE_URL = "http://someproject-001-site1.itempurl.com/";
    String URL_TOKEN = BASE_URL.concat("Register/token");
    String URL_PHONE_REG = BASE_URL.concat("api/User/Registration");
    String URL_REGISTRATION_FORM = BASE_URL.concat("api/User/RegistrationForm");
    String URL_GET_REALTY_TYPE = BASE_URL.concat("api/Directory/getRealtyType");
    String URL_GET_REQUEST_OFFERS = BASE_URL.concat("api/Directory/getRequestOffers");
    String URL_GET_RENT_PERIOD = BASE_URL.concat("api/Directory/getRentPeriod");
    String URL_GET_REALTY_PROPERTIES = BASE_URL.concat("api/Directory/getRealtyProperties");
    String URL_GET_DEAL_TYPE = BASE_URL.concat("api/Directory/getDealType");
    String URL_GET_COUNTRIES = BASE_URL.concat("api/Directory/getСountries");
    String URL_GET_USER_INFO = BASE_URL.concat("api/User/getUserInfo");
    String URL_FILTER_OFFERS = BASE_URL.concat("api/Realty/filterOffers");
    String URL_GET_RELEVANT_OFFERS = BASE_URL.concat("api/Realty/getRelevantOffers");
    String URL_RESERVE_NEW_REALTY = BASE_URL.concat("api/Realty/reservNewReality");
    String URL_UPDATE_REALTY = BASE_URL.concat("api/Realty/update");
    String URL_UPDATE_REALTY_PROPERTIES = BASE_URL.concat("api/Realty/updateRealtyProperties");
    String URL_UPDATE_REALTY_OFFERS = BASE_URL.concat("api/Realty/updateRealtyOffers");
    String URL_REALTY_USER_APPLICATIONS = BASE_URL.concat("api/Realty/UserAplications");
    String URL_GET_MY_SEARCHES = BASE_URL.concat("api/Search/getMySearches");
    String URL_SET_SEARCH_PUBLIC = BASE_URL.concat("api/Search/setSearchPublic");
    String URL_POST_IMAGE = BASE_URL.concat("api/Upload/PostImage");
    String URL_DELETE_IMAGE = BASE_URL.concat("api/Upload/deleteImage");
    String URL_PUBLISH_REALTY = BASE_URL.concat("api/Realty/publish");
    String URL_DELETE_REALTY = BASE_URL.concat("api/Realty/delete");
    String URL_PUSHER_AUTH = BASE_URL.concat("api/M2Pusher/authPusherChanel");
    String URL_PUSHER_GET_MY_CHATS = BASE_URL.concat("api/ChatPusher/getMyChats");
    String URL_PUSHER_CONVERSATION = BASE_URL.concat("api/ChatPusher/getConverstation");
    String URL_PUSHER_SEND_MESSAGE = BASE_URL.concat("api/ChatPusher/sendMessage");

    String SHARED_COUNTRIES = "SHARED_COUNTRY_INFO";
    String SHARED_PUSHER = "SHARED_PUSHER_INFO";
}
