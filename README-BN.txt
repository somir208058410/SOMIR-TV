SOMIR TV — Cinematic Edition
=============================

এই প্রজেক্টে আছে:
১. Cinematic dark navy Apple TV-inspired Home Screen
২. ঠিক পাঁচটি অপশন: YouTube, My File Manager, My Apps, Video Player, Wallpaper
৩. Bluetooth Remote / D-pad navigation এবং OK selection
৪. My Apps-এ ইনস্টল করা launchable অ্যাপের তালিকা
৫. Android-এর File Picker, Video Picker এবং Wallpaper Picker ব্যবহার

ইনস্টলেশন:
- Android Studio-তে এই ফোল্ডার Open করুন।
- Gradle Sync সম্পন্ন হতে দিন।
- Build > Build APK(s) নির্বাচন করুন।
- app/build/outputs/apk/debug/app-debug.apk ফাইলটি TV Box-এ ইনস্টল করুন।

গুরুত্বপূর্ণ:
এটি Source Project, APK নয়। এই পরিবেশে Android SDK দিয়ে APK compile করা হয়নি।
File Manager, Video Player ও Wallpaper অপশন Android TV Box-এর firmware-এ থাকা system picker-এর ওপর নির্ভরশীল।
ন্যূনতম Android API 21 নির্ধারণ করা হয়েছে। আপনার TV Box-এ পরীক্ষা করা প্রয়োজন।
