## Version 1.1

---

#### Library Changes

1. Removed ~~**Dagger2**~~ and added **koin** dependency injection. 

2. Removed ~~**Gson**~~ and added the **Kotlinx-Serialization** dependency. 

3. Removed ~~**HttpLoggingInterceptor**~~.

4. Added the **Crashlytics** Support.

5. Added the **Kotlinx-Serialization** library.


#### New Features

1. Added the support for Dual Sim configuration. 

2. Now you can cancel the on-going bulk sms operation.

3. Easy to track how many sms sent and how much remains with on-going notification.

4. Added the live update on History screen for Bulk-SMS operation change.

5. Added the support to send long sms.

6. Added the support to send bulk sms to at-least 1000 users. I already tested with 1000 numbers.

#### Bug Fixes

`[FIXED]` Performance update for sending Bulk SMS. 

`[FIXED]` Restart the Bulk SMS operation if the user closed the application during the on-going operation. Now you can simply close the application (if you want) after sending Bulk SMS the process won't restart from the begining. 

`[FIXED]` Application crash when sending long sms.

`[FIXED]` Application crash when sending BULK SMS to more than 100 contacts.
