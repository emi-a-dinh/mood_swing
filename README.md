**Description**

Our project is a mobile app aimed at increasing customer retition to the prestigious restaurant Moo'd Swing. The app features an enticing menu page, reservation 
capabilities that allow customers to instantly obtain limited and rare reservations, a rewards card to thank our most frequent customers and a settings page that allows for
complete customization of the UI. These features allow the restaurant to have a central location for customers to inform them about important updates such as menu changes, 
reservatin information, and more. Customers are to get notification 30 minutes prior to their reservation as a reminder. 


**Figma**

https://www.figma.com/design/KxEXXEsCqZYCs7c7MCg59e/Moo'd-Swing?node-id=2002-2&t=l87ByvedDstxTz4e-1

**Android and Jetpack Features**

- NavController
- Columns/Rows
- LazyColums/Rows
- Buttons
- Circular Progress Indicator
- Icons
- Images
- Bcrypt


**Above and Beyond**

App Storage
- We implemented a full app storage that would keep track of user sign ups. This has information such as their first and last name, their username, and their password. Their password is stored through a hash using bcypt. This allows for their passwords to be secured and not appear and an unencrypted string in the storage.
- It also keeps tracks of how many rewards the user has associated with it.
- It also keeps track of the reservation that the customer reserved.

Reservation System 
- The reservation system allows for the data to be rendered based on certain filters such as dates, time, and type (lunch or dinner) of reservation.
- Users get notified 30 minutes prior to their reservation time. This is not dependent on them being on the app, it will appear in the notification center.
  

