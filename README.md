# CS180project5Server
To compile the program you first start by running the server. Once the server is running you can run client1 and 
client2. You can run either one or both. Once the client is executed you can create account. After creating an account
you can log in. Once logged in you can go to settings or to stores. If you go to settings you can change your
username, password, email, role, or delete your account. If you go to stores you can create  a  store, add products, and view statistics. If you are a seller
you can search products, and buy products.

Server
The server stores the users arraylist and allows for user to sign in. Users can also edit their information
and the info is then sent to the server. The server checks to make sure that the user being signed into exist
with a matching username and password. If the user log in is valid the server tells the client to proceed
to the user interface. The server also passes that user to the client.

Client1
Client1 is how you start running the client.

Client2
Client2 is the same as client1

RunClient
Run client makes the socket, bufferReader, writer, objectInputStream, and objectOutPutStream for the 
client. Client 1 and 2 execute runClient. This creates a new thread. After everything is initalized the program
goes to LogInGUI

LogInGUI
This class houses the login gui. This gui allows for a user to sign in or go to a gui to register. An
user has to enter a valid username and password to get to the user interface. If the user presses the button to
register an account they are taken to register GUI.

RegisterGUI
This class has the GUI for a user to make a new account. The server gets sent the usesrname to make sure 
that the username is a unique username and not already taken. The RegisterGUI also makes sure that there
are no spaces in anything typed and all other attribrutes needed are there. Once registered the program goes
back to the log in GUI. The user can exit at any point too if they no longer want to make an account.

UserInterface
The user interface is accessed once a user signs in. The user is able to go to usersettings, stores, or log
out by pressing the apporiate button. The user is taken to the proper GUI once they select there option. User 
settings takes them to the settings GUI. Stores takes them to the customer or buyer gui depending on the role.
Logging out takes them to the log in GUI.

UserSettingGUI
This class allows for the user to change their information. All of their information will be updated throughout
the program if they change any of their information.

User
This class creates a User. A user contains an username, password, email, and role. There are getter
and setter methods that are used to get specific information from User variables.

UserNameExistError
If the username already exist when an account is being made this error is thrown.

ServerFileManager
This class allows for a file of the users to be written. This class also can read that file, so the users
are never lost and  can always be recovered.

Customer
This class defines the object of a customer with all its attributes using fields. Class used mainly
in the customerGUI to track purchases and save an arrayList as a file.

CustomerGUI
This class runs the interface on the customer side. You can access any store, see the data of the store,
make a booking, search using a keyword, and access other Customer interfaces as well.

CustomerPurchaseHistoryGUI
Class displays the purchase history of the current customer in a JTextArea. This is done by
accessing the file of the customer.

CustomerStatistics
Summarizes the transactions of the customer and displays them. Also allows for increasing and decreasing
sorts based on alphabetical order.

SellerGUI
Class runs the interface for the seller or agency manager. Allows adding, editing and deleting agencies.
You can also access other interfaces for the agency manager through this class.

editAgencyGUI
Class runs the interface for editing an agency on the seller side. Allows adding, deleting or editing
information for a tutor.

InvalidAgencyName
Exception if an invalid agency name is entered throughout the code.

InvalidTutor
Exception if an invalid tutor name is entered throughout the code.

SellerPurchaseHistoryGUI
Class runs the interface to show the seller purchase history from various agencies and various customers.

SellerStatistics
Class defines the object for seller statistics and its attributes as a field which is used in
the SellerStatisticsGUI.

SellerStatisticsGUI
Class runs the interface to show the seller statistics to summarize the purchase history and show
the transactions done by the agency manager, each agency and each customer in that agency.

Tutor
Class defines the object for Tutor and its attributes. It is used in various other classes to read files
for agencies and store information in multiple Tutor objects.

Bryce will submit the project report, code, and presentation
