JEEP
====

Java-Exclusive Email Program (JEEP)

 This email client will require configuration by the user before functioning properly.

 Simply open MailConfig.properties and GMailConfig.properties and enter the omitted settings.
 
 For example:
 
    loginPOP3=*login*
    passwordPOP3=*password*
    userName=*username*
    userEmail=*email address*
    dbServer=*databse server*
    dbName=*database name*
    dbPort=*database port*
    dbLogin=*database user*
    dbPassword=*database password*

To launch the application, simply run EmialApp.java

Known Issues:
  1. Images link in Help menu are broken.
  2. Drag and drop can detect valid drop locations, but update doesn't trigger.
  3. Was not able to fix my issue my RegEx's displaying my JFormattedTextFields
  	  so I disabled any RegEx/Mask validation to make the app. easier to handle when testing.
  4. Localization is practically non-existent. I started at the beginning of the project and
     never got back to it in time.
  5. Contacts windows doens't update on Contact delete, thought it does for Contact add.
