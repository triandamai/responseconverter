# responseconverter

#rilis pertama

   Di Class ini akan digunakan sebagai converter dari response retrofit tanpa perlu banyak baris code
   Contoh kita punya json:
```java
  {
    "status": 200,
    "data": [
        {
            "id_user": "sgdfhgjhjkl",
            "user_name": "Trian",
            "user_email": "trian@gmail.com"
            ....
        }
    ],
    "success": true,
    "message": "Berhasil"
}
```
Untuk mengambil object  
```
{
"data":[
   ....
  ]
} 
```
Cukup dengan code:
```java 
List<UserModel> u =  converter.getData(UserModel.class); 
```
Cara pakai
```java
//inisiasi
Buat sebuah class yang meng extend MyConverter

MyConverter converter = MyConverter.create();

//Pemakaian pada retrofit (misal)
 apiservice.prosesLogin(req)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //getData jika data json berupa array
                        //getSingleData jika json single object
                        converter.check(response);
                        List<UserModel> u =  converter.getData(UserModel.class);
                      
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
```

Pada ```java.class ``` turunan ```override``` RES_CODE,RES_STATUS sesuai dengan property json kalian
# Persistensi Data
Pada ``Manifest.xml`` Tambahkan Provider:
``
<provider
            android:authorities=".InitConverter"
            android:name="com.triandamai.converter.InitConverter"/>``

Cara install

# Tambahkan JitPack repository ke build file
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
# Tambahkan dependency
```
dependencies {
	        implementation 'com.github.triandamai:responseconverter:v1.0.4'
	}
```


