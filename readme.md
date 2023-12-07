# Farklı Frontend Kütüphaneleri ile Kodlanmış Hava Durumu Uygulaması

> Uygulama, farklı frontend kütüphanelerini kullanarak kodlama yapmak isteyenler için bir örnek olması amacıyla hazırlanmıştır.

## Uygulama Hakkında

Son derece basit bir işlevi olacak uygulamadan beklenen şey, kullanıcının bulunduğu konumun hava durumunu göstermek. Uygulamada başlangıç aşamasında beklenen iki özellik var, Kullanıcının bulunduğu konumun ve saatin;

- Hava sıcaklığını celcius cinsinden göstermek
- Havayı temsil eden bir ikon göstermek

## Teknik Detaylar

- Uygulamanın kullanıcının konumuna erişmesi ve konum bilgilerini enlem boylam cinsinden alması gerekiyor. İlk aşamada kullanıcıdan konum bilgilerini almak yerine, konum bilgileri manuel olarak kodlara yazılacak.
- Alınan konum bilgileri ile API isteği atılarak hava durumu bilgileri alınacak.
- Hava durumu bilgileri JSON formatında olacağı için kullanılacak teknolojide bunu direk işleyecek bir yapı yoksa JSON formatındaki verileri işleyebilecek bir kütüphane kullanılacak.
- Uygulama tasarımı ve kullanılacak ikonlar buradaki figma dosyasında kabaca gösterilmiş. [Local Figma Dosyası](./Simple_Weather_App_Design_Community.fig) ve [Online Figma Dosyası](https://www.figma.com/community/file/1176173521127853129)
- Uygulama için kullanılacak API servisi [Open Meteo](https://open-meteo.com/) olacak.

## Uygulamanın Kodlanması İçin Kullanılacak Teknolojiler

- Native Android (Kotlin) -Arayüz Jetpack Compose ile kodlanacak-
- Flutter (Test Driven Development ile kodlanacak)
- Angular
- React
- Vue
- Native Android (Java) -Arayüz için XML kullanılacak-
- Native iOS (Swift) -Arayüz için Storyboard kullanılacak-
- Native iOS (Swift) -Arayüz için SwiftUI kullanılacak-

### Tüm kodlar ve süreçler bu repository içerisinde paylaşılacak. Her bir teknoloji için ayrı bir branch oluşturulacak.
