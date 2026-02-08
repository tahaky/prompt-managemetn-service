# AI System Prompt Management Service

Bu proje, Java 17 ve Spring Boot 3.2 kullanılarak geliştirilmiş bir AI sistem prompt yönetim servisidir. Servis, AI entegrasyon servislerinin güncel sistem promptlarını alabilmesi için API'ler sağlar.

## Özellikler

- ✅ AI sistem promptlarını oluşturma, güncelleme, okuma ve silme (CRUD)
- ✅ Prompt versiyonlama sistemi
- ✅ AI entegrasyon servisleri için özel endpoint
- ✅ Kategori bazlı prompt yönetimi
- ✅ Aktif/pasif prompt durumu yönetimi
- ✅ H2 in-memory veritabanı desteği
- ✅ RESTful API tasarımı
- ✅ Kapsamlı hata yönetimi
- ✅ Unit testler

## Teknolojiler

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**
- **JUnit 5 & Mockito**

## Başlangıç

### Gereksinimler

- Java 17 veya üzeri
- Maven 3.6 veya üzeri

### Projeyi Çalıştırma

1. Projeyi klonlayın:
```bash
git clone https://github.com/tahaky/prompt-managemetn-service.git
cd prompt-managemetn-service
```

2. Projeyi derleyin:
```bash
mvn clean install
```

3. Uygulamayı başlatın:
```bash
mvn spring-boot:run
```

Uygulama varsayılan olarak `http://localhost:8080` adresinde çalışacaktır.

### H2 Console

H2 veritabanı konsolu `http://localhost:8080/h2-console` adresinden erişilebilir.

**Bağlantı Bilgileri:**
- JDBC URL: `jdbc:h2:mem:promptdb`
- Kullanıcı Adı: `sa`
- Şifre: (boş)

## API Endpoints

### Prompt Yönetimi API'leri

#### 1. Yeni Prompt Oluşturma
```http
POST /api/prompts
Content-Type: application/json

{
  "name": "chatbot-greeting",
  "content": "You are a helpful AI assistant. Always greet users warmly.",
  "category": "chatbot",
  "active": true
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "chatbot-greeting",
  "content": "You are a helpful AI assistant. Always greet users warmly.",
  "category": "chatbot",
  "version": 1,
  "active": true,
  "createdAt": "2024-02-08T15:30:00",
  "updatedAt": "2024-02-08T15:30:00"
}
```

#### 2. Prompt Güncelleme (Yeni Versiyon)
```http
PUT /api/prompts/{name}
Content-Type: application/json

{
  "content": "You are a helpful AI assistant. Always be polite and professional.",
  "category": "chatbot",
  "active": true
}
```

**Not:** Güncelleme işlemi eski versiyonu pasif hale getirir ve yeni bir versiyon oluşturur.

#### 3. İsme Göre Aktif Prompt Getirme
```http
GET /api/prompts/{name}
```

#### 4. ID'ye Göre Prompt Getirme
```http
GET /api/prompts/id/{id}
```

#### 5. Tüm Aktif Promptları Listeleme
```http
GET /api/prompts
```

#### 6. Kategoriye Göre Promptları Listeleme
```http
GET /api/prompts/category/{category}
```

#### 7. Prompt Versiyon Geçmişini Görme
```http
GET /api/prompts/{name}/versions
```

#### 8. Prompt Silme (Pasif Hale Getirme)
```http
DELETE /api/prompts/{name}
```

### AI Entegrasyon API'leri

#### AI Servisleri için Güncel Prompt Alma
```http
GET /api/integration/prompts/{name}
```

Bu endpoint, AI entegrasyon servislerinin güncel ve aktif promptları alması için özel olarak tasarlanmıştır.

#### Health Check
```http
GET /api/integration/health
```

## Örnek Kullanım Senaryosu

### 1. Chatbot için Prompt Oluşturma
```bash
curl -X POST http://localhost:8080/api/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "customer-support-bot",
    "content": "You are a customer support AI. Be helpful and professional.",
    "category": "customer-service",
    "active": true
  }'
```

### 2. AI Servisi Güncel Promptu Alıyor
```bash
curl http://localhost:8080/api/integration/prompts/customer-support-bot
```

### 3. Promptu Güncelleme
```bash
curl -X PUT http://localhost:8080/api/prompts/customer-support-bot \
  -H "Content-Type: application/json" \
  -d '{
    "content": "You are a customer support AI. Be helpful, professional, and empathetic.",
    "active": true
  }'
```

### 4. AI Servisi Güncellenmiş Promptu Alıyor
```bash
curl http://localhost:8080/api/integration/prompts/customer-support-bot
```

Yeni versiyon (version 2) döndürülür.

## Veri Modeli

### Prompt Entity

| Alan | Tip | Açıklama |
|------|-----|----------|
| id | Long | Benzersiz tanımlayıcı |
| name | String | Prompt adı (benzersiz) |
| content | String | Prompt içeriği |
| category | String | Prompt kategorisi |
| version | Integer | Versiyon numarası |
| active | Boolean | Aktif/pasif durumu |
| createdAt | LocalDateTime | Oluşturulma zamanı |
| updatedAt | LocalDateTime | Güncellenme zamanı |

## Versiyonlama

Servis, prompt güncellemelerinde otomatik versiyonlama yapar:
- Her güncelleme yeni bir versiyon oluşturur
- Eski versiyon pasif (active=false) hale getirilir
- Yeni versiyon aktif (active=true) olarak işaretlenir
- Version numarası otomatik olarak artırılır

## Testler

Tüm testleri çalıştırmak için:
```bash
mvn test
```

Sadece bir test sınıfını çalıştırmak için:
```bash
mvn test -Dtest=PromptServiceTest
```

## Hata Kodları

| HTTP Kodu | Açıklama |
|-----------|----------|
| 200 | Başarılı |
| 201 | Başarıyla oluşturuldu |
| 204 | İçerik yok (silme başarılı) |
| 400 | Hatalı istek (validasyon hatası) |
| 404 | Bulunamadı |
| 409 | Çakışma (aynı isimde prompt zaten var) |
| 500 | Sunucu hatası |

## Geliştirme

### Kod Yapısı
```
src/main/java/com/tahaky/promptmanagement/
├── controller/          # REST API endpoints
├── service/            # Business logic
├── repository/         # Data access layer
├── model/              # Entity classes
├── dto/                # Data Transfer Objects
└── exception/          # Exception handling
```

### Katkıda Bulunma

1. Bu repository'yi fork edin
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## İletişim

Proje Sahibi: tahaky
GitHub: [@tahaky](https://github.com/tahaky)
