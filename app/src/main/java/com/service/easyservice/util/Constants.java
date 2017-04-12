package com.service.easyservice.util;

/**
 * Created by Smile on 05-04-2016.
 */
public interface Constants {

    int SPLASH_DURATION = 3000; //duration is in milliseconds
    int LOGIN_SLIDER_DURATION = 3000; //duration is in milliseconds


    int LOCATION_HOME = 101;
    int LOCATION_WORK = 102;
    int LOCATION_OTHER = 103;

    // local url
//    String URL = "http://52.24.208.146/app/";
    String URL= "http://easyservices.me/app/";

    String REQUEST_OTP = URL+"api-get-otp/";
    String VERIFY_OTP = URL+"api-verify-otp/";
    String ADD_APPLIANCE_USER_URL = URL + "api-addappliance/";
    String DASHBOARD_DETAILS_URL = URL+"api-dashboard/";
    String MY_DEVICE_URL = URL+"api-myappliance/";
    String DELETE_DEVICE_URL = URL+"api-deleteappliance/";
    String GET_TIME_SLOT_URL = URL+"api-getdatetimeslot/";
    String REQUEST_SERVICE_URL = URL+"api-addrequest/";
    String REQUEST_SERVICE_LIST_URL = URL+"api-myrequest/";
    String SUPPORT_TICKET_URL = URL+"api-add-ticket/";
    String UPDATE_PROFILE_URL = URL+"api-profileupdate/";
    String MY_REQUEST_FEEDBACK_URL = URL + "api-ratingfeedback/";
    String ENGINEER_LOCATION_URL = URL + "api-get-engglocation/";




    /* Required Fonts */
    String F_OPEN_SANS_BOLD = "font/OpenSans-Bold.ttf";
    String F_OPEN_SANS_BOLD_ITALIC = "font/OpenSans-BoldItalic.ttf";
    String F_OPEN_SANS_EXTRA_BOLD = "font/OpenSans-ExtraBold.ttf";
    String F_OPEN_SANS_EXTRA_BOLD_ITALIC = "font/OpenSans-ExtraBoldItalic.ttf";
    String F_OPEN_SANS_ITALIC = "font/OpenSans-Italic.ttf";
    String F_OPEN_SANS_LIGHT = "font/OpenSans-Light.ttf";
    String F_OPEN_SANS_LIGHT_ITALIC = "font/OpenSans-LightItalic.ttf";
    String F_OPEN_SANS_REGULAR = "font/OpenSans-Regular.ttf";
    String F_OPEN_SANS_SEMI_BOLD = "font/OpenSans-Semibold.ttf";
    String F_OPEN_SANS_SEMI_BOLD_ITALIC = "font/OpenSans-SemiboldItalic.ttf";

    String CHECK_INTERNET_CONNECTION = "Internet connection is not available or request timed out.";
    String RESPONSE_ISSUE_MESSAGE = "We're sorry but an unexpected error occurred while processing your request. Please try again";

    String VALID_FIELD = "Please enter a valid ";
    String INSERT_FIELD = "Please enter the ";

    String EMAIL_ADDRESS = "email address";
    String PASSWORD = "password";
    String LOGIN_ACTION = "login";
    String FACEBOOOK_LOGIN_ACTION = "facebook_login";
    String MY_APPLIANCE_ACTION = "myappliance";
    String EDIT_PROFILE_ACTION = "editprofile";

        //added by smit
    String TAG = "EasyService";
    String REGISTER_ACTION = "register";
    String DASHBOARD_ACTION = "dashboard";
    String ADD_APPLIANCE_DETAILS_ACTION = "addapplianceinfoaction";
    String ADD_APPLIANCE_ACTION = "addapplianceaction";
    String ISSUE_DETAILS_ACTION = "issueinfoeaction";
    String TIME_SLOT_DETAILS_ACTION = "timeslotinfoeaction";
    String ADD_REQUEST_ACTION = "addrequestaction";
    String DELETE_APPLIANCE_ACTION = "deleteapplianceaction";
    String MY_REQUESTS_ACTION = "myrequestaction";
    String MY_REQUESTS_FEEDBACK_ACTION = "feedbackaction";
    String EDIT_APPLIANCE_ACTION = "editapplianceaction";
    String FORGOT_PASSWORD_ACTION = "forgotpasswordaction";
    String UPDATE_PROFILE_ACTION = "updateprofileaction";
    String COUNTRY_STATE_CITY_DETAILS_ACTION = "countrystatecitydetails";


    int REQUEST_CAMERA = 1000;
    int SELECT_FILE = 2000;
    String BRAND_CATEGORY_JSON = "{\"category\": [{\"category_name\": \"mobile\",\"brand\": [{\t\"brand_name\": \"APPLE\",\t\"model\": [\"iPad 2 CDMA\",\"iPad 2 Wi-Fi\",\"iPad 2 Wi-Fi + 3G\",\"iPad 3 Wi-Fi\",\"iPad 3 Wi-Fi + Cellular\",\"iPad 4 Wi-Fi\",\"iPad 4 Wi-Fi + Cellular\",\"iPad Air\",\"iPad Air 2\",\"iPad mini 2\",\"iPad mini 3\",\"iPad mini 4\",\"iPad mini Wi-Fi\",\"iPad mini Wi-Fi + Cellular\",\"iPad Pro\",\"iPad Pro 9\",\"iPad Wi-Fi\",\"iPad Wi-Fi + 3G\",\"iPhone\",\"iPhone 3G\",\"iPhone 3GS\",\"iPhone 4\",\"iPhone 4 CDMA\",\"iPhone 4s\",\"iPhone 5\",\"iPhone 5c\",\"iPhone 5s\",\"iPhone 6\",\"iPhone 6 Plus\",\"iPhone 6s\",\"iPhone 6s Plus\",\"iPhone 7\",\"iPhone 7 Plus\",\"iPhone SE\",\"Watch 38mm\",\"Watch 42mm\",\"Watch Edition 38mm\",\"Watch Edition 42mm\",\"Watch Edition Series 2 38mm\",\"Watch Edition Series 2 42mm\",\"Watch Series 1 Sport 38mm\",\"Watch Series 1 Sport 42mm\",\"Watch Series 2 38mm\",\"Watch Series 2 42mm\",\"Watch Series 2 Sport 38mm\",\"Watch Series 2 Sport 42mm\",\"Watch Sport 38mm\",\"Watch Sport 42mm\"]},{\t\"brand_name\": \"SAMSUNG\",\t\"model\": [\"Galaxy A7 \",\"Galaxy A5 \",\"Galaxy A3 \",\"Galaxy Grand Prime Plus\",\"Galaxy J2 Prime\",\"Galaxy C9 Pro\",\"Galaxy A8 \",\"Galaxy On8\",\"Galaxy On7 \",\"Gear S3 classic\",\"Gear S3 frontier\",\"Gear S3 frontier LTE\",\"Galaxy J5 Prime\",\"Galaxy J7 Prime\",\"Z2\",\"Galaxy Note7 (USA)\",\"Galaxy Note7\",\"Galaxy On7 Pro\",\"Galaxy On5 Pro\",\"Galaxy Tab J\",\"Galaxy J Max\",\"Galaxy J2 Pro \",\"Galaxy J2 \",\"Z3 Corporate Edition\",\"Galaxy Xcover 3 G389F\",\"Galaxy S7 active\",\"Galaxy J3 Pro\",\"Galaxy C7\",\"Galaxy C5\",\"Galaxy A9 Pro \",\"Galaxy J7 \",\"Galaxy J5 \",\"Galaxy Tab A 10.1 \",\"Galaxy Tab A 7.0 \",\"Galaxy S7\",\"Galaxy S7 edge\",\"Galaxy S7 edge (USA)\",\"Galaxy S7 (USA)\",\"Galaxy J1 Nxt\",\"Gear S2 classic 3G\",\"Galaxy Tab E 8.0\",\"Galaxy J1 \",\"Galaxy A9 \",\"Galaxy A7 \",\"Galaxy A5 \",\"Galaxy A3 \",\"Galaxy Express Prime\",\"Galaxy J3 \",\"Galaxy View\",\"Galaxy On7\",\"Galaxy On5\",\"Z3\",\"Galaxy J1 Ace\",\"Gear S2 classic\",\"Gear S2\",\"Gear S2 3G\",\"Galaxy Note5 (USA)\",\"Galaxy Note5\",\"Galaxy Note5 Duos\",\"Galaxy S6 edge+ (USA)\",\"Galaxy S6 edge+\",\"Galaxy S6 edge+ Duos\",\"Galaxy S5 Neo\",\"Galaxy S4 mini I9195I\",\"Galaxy Folder\",\"Galaxy Tab S2 9.7\",\"Galaxy Tab S2 8.0\",\"Galaxy A8 Duos\",\"Galaxy A8\",\"Galaxy V Plus\",\"Galaxy J7\",\"Galaxy J5\",\"Galaxy Tab 4 10.1 \",\"Galaxy Tab E 9.6\",\"Galaxy S6 active\",\"Galaxy Tab 3 V\",\"Galaxy Tab A & S Pen\",\"Galaxy Tab A 9.7\",\"Galaxy Tab A 8.0\",\"Galaxy Xcover 3\",\"Galaxy S6 edge (USA)\",\"Galaxy S6 (USA)\",\"Galaxy S6 edge\",\"Galaxy S6 Plus\",\"Galaxy S6 Duos\",\"Galaxy S6\",\"Galaxy J1 4G\",\"Galaxy J1\",\"Galaxy J2\",\"Galaxy Tab 3 Lite 7.0 VE\",\"Z1\",\"Galaxy A7 Duos\",\"Galaxy A7\",\"Galaxy Grand Max\",\"Galaxy E7\",\"Galaxy E5\",\"Galaxy Core Prime\",\"Galaxy A5 Duos\",\"Galaxy A5\",\"Galaxy A3 Duos\",\"Galaxy A3\",\"Galaxy S5 Plus\",\"Galaxy Pocket 2\",\"Galaxy V\",\"Galaxy Grand Prime Duos TV\",\"Galaxy Grand Prime\",\"Galaxy Ace Style LTE G357\",\"Galaxy Note Edge\",\"Galaxy Note 4 Duos\",\"Galaxy Note 4 (USA)\",\"Galaxy Note 4\",\"Galaxy Tab Active LTE\",\"Galaxy Tab Active\",\"Galaxy Mega 2\",\"Gear S\",\"Gear 2 Neo\",\"Gear Live\",\"Gear 2\",\"Galaxy Gear\",\"Galaxy S5 LTE-A G901F\",\"Galaxy Alpha (S801)\",\"Galaxy Alpha\",\"Galaxy S5 mini Duos\",\"Galaxy Avant\",\"Galaxy S Duos 3\",\"Galaxy Ace NXT\",\"Galaxy Tab 4 8.0 \",\"Galaxy Star 2 Plus\",\"Galaxy S5 mini\",\"Galaxy Ace 4 LTE G313\",\"Galaxy Ace 4\",\"Galaxy Young 2\",\"Galaxy Star 2\",\"Galaxy Core II\",\"Galaxy S5 Sport\",\"Galaxy S5 LTE-A G906S\",\"Galaxy Tab S 8.4 LTE\",\"Galaxy Tab S 8.4\",\"Galaxy Tab S 10.5 LTE\",\"Galaxy Tab S 10.5\",\"Galaxy Core Lite LTE\",\"I9301I Galaxy S3 Neo\",\"Galaxy W\",\"Z\",\"Galaxy S5 Active\",\"Galaxy K zoom\",\"Galaxy Beam2\",\"I9300I Galaxy S3 Neo\",\"Galaxy Ace Style\",\"ATIV SE\",\"Galaxy Tab 4 7.0\",\"Galaxy Tab 4 7.0 3G\",\"Galaxy Tab 4 7.0 LTE\",\"Galaxy Tab 4 8.0\",\"Galaxy Tab 4 8.0 3G\",\"Galaxy Tab 4 8.0 LTE\",\"Galaxy Tab 4 10.1\",\"Galaxy Tab 4 10.1 3G\",\"Galaxy Tab 4 10.1 LTE\",\"G3812B Galaxy S3 Slim\",\"I8200 Galaxy S III mini VE\",\"Galaxy S5 Duos\",\"Galaxy S5 (octa-core)\",\"Galaxy S5 (USA)\",\"Galaxy S5\",\"Galaxy Core LTE G386W\",\"Galaxy Core LTE\",\"Galaxy Star Trios S5283\",\"Galaxy Note 3 Neo Duos\",\"Galaxy Note 3 Neo\",\"Galaxy Y Plus S5303\",\"Galaxy Young S6310\",\"Galaxy Fame S6810\",\"Galaxy Express I8730\",\"S7710 Galaxy Xcover 2\",\"I9105 Galaxy S II Plus\",\"Ativ Odyssey I930\",\"Galaxy Grand I9082\",\"Galaxy Grand I9080\",\"Galaxy Note LTE 10.1 N8020\",\"Galaxy Axiom R830\",\"Galaxy Stratosphere II I415\",\"Galaxy Discover S730M\",\"Galaxy Pop SHV-E220\",\"Galaxy Premier I9260\",\"Google Nexus 10 P8110\",\"Ativ Tab P8510\",\"I8190 Galaxy S III mini\",\"Galaxy Music S6010\",\"Galaxy Music Duos S6012\",\"Galaxy Rugby Pro I547\",\"Galaxy Express I437\",\"I9305 Galaxy S III\",\"Galaxy Victory 4G LTE L300\",\"Galaxy S Relay 4G T699\",\"Galaxy Pocket Duos S5302\",\"R730 Transfix\",\"i927 Captivate Glide\",\"DoubleTime I857\",\"M930 Transform Ultra\",\"P6210 Galaxy Tab 7.0 Plus\",\"P6200 Galaxy Tab 7.0 Plus\",\"Omnia W I8350\",\"Galaxy S II HD LTE\",\"P6810 Galaxy Tab 7.7\",\"P6800 Galaxy Tab 7.7\",\"Galaxy Note N7000\",\"Galaxy S II I777\",\"Galaxy S II X T989D\",\"Galaxy S II T989\",\"Galaxy S II Epic 4G Touch\",\"S8600 Wave 3\",\"Wave M S7250\",\"Wave Y S5380\",\"Galaxy S II LTE i727R\",\"Galaxy S II LTE I9210\",\"Galaxy Tab 8.9 LTE I957\",\"Galaxy W I8150\",\"Galaxy M Pro B7800\",\"Galaxy Y Pro B5510\",\"Galaxy Y TV S5367\",\"Galaxy Y S5360\",\"Galaxy Q T589R\",\"S5690 Galaxy Xcover\",\"Galaxy S II 4G I9100M\",\"I9103 Galaxy R\",\"R720 Admire\",\"Conquer 4G\",\"Gravity SMART\",\"Exhibit 4G\",\"Dart T499\",\"I9001 Galaxy S Plus\",\"M580 Replenish\",\"Galaxy Prevail\",\"M220L Galaxy Neo\",\"Google Nexus S I9020A\",\"Google Nexus S I9023\",\"P1010 Galaxy Tab Wi-Fi\",\"P7500 Galaxy Tab 10.1 3G\",\"Galaxy Tab 10.1 LTE I905\",\"Galaxy Tab 10.1 P7510\",\"Galaxy Tab 8.9 P7300\",\"Galaxy Tab 8.9 P7310\",\"Google Nexus S 4G\",\"Galaxy Pro B7510\",\"I9100 Galaxy S II\",\"S5780 Wave 578\",\"P7100 Galaxy Tab 10.1v\",\"Galaxy S WiFi 5.0\",\"I9003 Galaxy SL\",\"Galaxy Ace S5830I\",\"Galaxy Ace S5830\",\"Galaxy Fit S5670\",\"Galaxy Gio S5660\",\"Galaxy Mini S5570\",\"Galaxy Pop i559\",\"Galaxy S 4G T959\",\"I997 Infuse 4G\",\"R910 Galaxy Indulge\",\"Droid Charge I510\",\"Google Nexus S\",\"M190S Galaxy S Hoppin\",\"I9010 Galaxy S Giorgio Armani\",\"I100 Gem\",\"Continuum I400\",\"Focus\",\"I8700 Omnia 7\",\"S5750 Wave575\",\"S8530 Wave II\",\"M210S Wave2\",\"Galaxy Tab T-Mobile T849\",\"P1000 Galaxy Tab\",\"Galaxy Tab CDMA P100\",\"Galaxy Tab 4G LTE\",\"I909 Galaxy S\",\"M130K Galaxy K\",\"Mesmerize i500\",\"M920 Transform\",\"Galaxy 551\",\"S7230E Wave 723\",\"M130L Galaxy U\",\"Fascinate\",\"Vibrant\",\"i897 Captivate\",\"B7350 Omnia PRO 4\",\"B6520 Omnia PRO 5\",\"Epic 4G\",\"Acclaim\",\"Intercept\",\"i225 Exec\",\"S5330 Wave533\",\"S5250 Wave525\",\"I5500 Galaxy 5\",\"I5801 Galaxy Apollo\",\"I5800 Galaxy 3\",\"M110S Galaxy S\",\"Galaxy A\",\"I9000 Galaxy S\",\"I6500U Galaxy\",\"S8500 Wave\",\"I8520 Galaxy Beam\",\"M715 T*OMNIA II\",\"I5700 Galaxy Spica\",\"B7620 Giorgio Armani\",\"T939 Behold 2\",\"Vodafone 360 H1\",\"Vodafone 360 M1\",\"B7330 OmniaPRO\",\"i220 Code\",\"M900 Moment\",\"i350 Intrepid\",\"S9110\",\"I8000 Omnia II\",\"B7610 OmniaPRO\",\"B7320 OmniaPRO\",\"B7300 OmniaLITE\",\"i637 Jack\",\"I7500 Galaxy\",\"Propel Pro\",\"i8910 Omnia HD\",\"C6625\",\"i770 Saga\",\"C6620\",\"i907 Epix\",\"i7110\",\"i8510 INNOV8\",\"i740\",\"i900 Omnia\",\"L870\",\"G810\",\"i200\",\"SPH-i325 Ace\",\"i640\",\"i780\",\"i560\",\"i550\",\"i450\",\"i620\",\"i400\",\"i710\",\"i520\",\"i600\",\"i617 BlackJack II\",\"i607 BlackJack\",\"i320\",\"i310\",\"Z600\",\"i300x\",\"D730\",\"i750\",\"D720\",\"i300\",\"D710\",\"i530\",\"i505\",\"i500\",\"i250\",\"i700\",\"D700\",\"Watch Phone\",\"Galaxy Tab 3 Plus 10.1 P8220\",\"Galaxy F\",\"Galaxy Grand 3\",\"Galaxy S7 mini\",\"Galaxy On5 \"]},{\t\"brand_name\": \"MOTOROLA\",\t\"model\": [\"Moto 360 (1st gen)\",\"Moto 360 42mm (2nd gen)\",\"Moto 360 46mm (2nd gen)\",\"Moto 360 Sport (1st gen)\",\"Moto E\",\"Moto E (2nd gen)\",\"Moto E Dual SIM\",\"Moto E Dual SIM (2nd gen)\",\"Moto E3\",\"Moto E3 Power\",\"Moto G\",\"Moto G (2nd gen)\",\"Moto G (3rd gen)\",\"Moto G 4G\",\"Moto G 4G (2nd gen)\",\"Moto G 4G Dual SIM (2nd gen)\",\"Moto G Dual SIM\",\"Moto G Dual SIM (2nd gen)\",\"Moto G Dual SIM (3rd gen)\",\"Moto G Turbo Edition\",\"Moto G4\",\"Moto G4 Play\",\"Moto G4 Plus\",\"Moto M\",\"Moto Maxx\",\"Moto X\",\"Moto X (2nd Gen)\",\"Moto X Force\",\"Moto X Play\",\"Moto X Play Dual SIM\",\"Moto X Style\",\"Moto Z\",\"Moto Z Force\",\"Moto Z Play\",\"Nexus 6\"]},{\t\"brand_name\": \"LENOVO\",\t\"model\": [\"A Plus\",\"A1000\",\"A10-70 A7600\",\"A185\",\"A1900\",\"A2010\",\"A269i\",\"A316i\",\"A319\",\"A328\",\"A335\",\"A336\",\"A3690\",\"A369i\",\"A390\",\"A3900\",\"A5000\",\"A516\",\"A526\",\"A536\",\"A5860\",\"A60\",\"A60+\",\"A6000\",\"A6000 Plus\",\"A6010\",\"A6010 Plus\",\"A606\",\"A616\",\"A630\",\"A65\",\"A660\",\"A6600\",\"A6600 Plus\",\"A680\",\"A690\",\"A7000\",\"A7000 Plus\",\"A7000 Turbo\",\"A706\",\"A7-30 A3300\",\"A7-50 A3500\",\"A789\",\"A800\",\"A820\",\"A830\",\"A850\",\"A8-50 A5500\",\"A850+\",\"A859\",\"A880\",\"A889\",\"A916\",\"B\",\"C2\",\"C2 Power\",\"E156\",\"Golden Warrior A8\",\"Golden Warrior Note 8\",\"Golden Warrior S8\",\"ideapad\",\"IdeaPad A1\",\"IdeaPad K1\",\"IdeaPad S2\",\"IdeaTab A1000\",\"IdeaTab A2107\",\"IdeaTab A3000\",\"IdeaTab S6000\",\"IdeaTab S6000F\",\"IdeaTab S6000H\",\"IdeaTab S6000L\",\"K3\",\"K3 Note\",\"K5 Note\",\"K6\",\"K6 Note\",\"K6 Power\",\"K80\",\"K800\",\"K860\",\"K900\",\"Lemon 3\",\"LePad S2005\",\"LePad S2007\",\"LePad S2010\",\"LePhone S2\",\"P2\",\"P70\",\"P700i\",\"P770\",\"P780\",\"P90\",\"Phab\",\"Phab Plus\",\"Phab2\",\"Phab2 Plus\",\"Phab2 Pro\",\"Q330\",\"Q350\",\"S5000\",\"S560\",\"S580\",\"S60\",\"S650\",\"S660\",\"S720\",\"S750\",\"S800\",\"S820\",\"S850\",\"S856\",\"S860\",\"S880\",\"S890\",\"S90 Sisley\",\"S920\",\"S930\",\"S939\",\"Tab 2 A10-70\",\"Tab 2 A7-10\",\"Tab 2 A7-20\",\"Tab 2 A7-30\",\"Tab 2 A8-50\",\"Tab S8\",\"Tab3 10\",\"Tab3 7\",\"Tab3 8\",\"ThinkPad\",\"Vibe A\",\"Vibe C\",\"Vibe K4 Note\",\"Vibe K5\",\"Vibe K5 Plus\",\"Vibe P1\",\"Vibe P1 Turbo\",\"Vibe P1m\",\"Vibe S1\",\"Vibe S1 Lite\",\"Vibe Shot\",\"Vibe X S960\",\"Vibe X2\",\"Vibe X2 Pro\",\"Vibe X3\",\"Vibe X3 c78\",\"Vibe Z K910\",\"Vibe Z2\",\"Vibe Z2 Pro\",\"Vibe Z3 Pro\",\"Yoga Tab 3 8\",\"Yoga Tab 3 Plus\",\"Yoga Tablet 10\",\"Yoga Tablet 10 HD+\",\"Yoga Tablet 2 10\",\"Yoga Tablet 2 8\",\"Yoga Tablet 2 Pro\",\"Yoga Tablet 8\",\"ZUK Edge\",\"ZUK Z1\",\"ZUK Z2\",\"ZUK Z2 Pro\"]}]}]}";


}
