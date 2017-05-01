# RapidDvpt
## 工具类
* Base64Utils
	* decode解码base64编码后的字符串
	* encode对字节数组进行base64编码
	* 支持把文件读取成base64字符串
* MD5Utils
	* 支持根据字符串生成32位MD5
* RSAUtils
	* 支持根据key对公钥进行RSA加密
	* 支持根据key对私钥进行RSA加密
	* 支持根据key对加密字节数组进行RC4解密
* RC4Utils
	* 支持根据key对字符串进行RC4加密
	* 支持根据key对字符串进行RC4解密
	* 根据随机数生成RC4key
* BitmapUtils
	* 支持按照特定宽高或者倍数scale缩放
	* 支持rotate旋转
	* 支持把bitmap读取成byte[]
	* 支持对bitmap高斯模糊处理
	* 支持给定宽高计算bitmap的压缩比率
	* 支持防止oom的读取bitmap方式
* ResourceUtils
	* 根据资源类型和名称返回资源id
	* 从assets中读取文件返回字符串
	* 从assets中bitmap图片
* CrashUtils
	* 需要在application初始化
	* 支持捕获crash异常并toast提示，收集crash异常日志保存本地
* DisplayUtils
	* 支持sp/dp与px的相互转换
	* 支持获取屏幕/屏幕水平/屏幕竖直dpi
	* 支持获取屏幕密度比(0.5,1.0,1.5等)
	* 支持获取屏幕每英寸的像素点(120,160,240,320等)
* FileUtils
	* 支持判断文件是否存在
	* 支持递归删除文件
	* 支持获取一个文件的大小，小于１Ｍ返回ｎＫ，否则返回ｎM
	* 支持获取一个文件的字节大小
	* 支持获取一个文件的大小，可选择返回B,KB,MB,GB为单位
	* 支持根据uri获取path
	* 支持把文件解压到特定路径
	* 支持把字符串以文件形式保存于特定路径
	* 支持从压缩文件中读取特定的文件，以字符串的形式返回 
* HtmlUtils
	* 支持过滤特定字符串中的特殊字符
	* 支持判断一个字符是否是字母
	* 支持判断一个字符是否是数字
	* 支持判断一个字符是否是16进制字符
	* 支持判断一个字符是否是空白字符
* JsonUtils
	* 支持根据map生成json格式的字符串
	* 支持根据key从JSONObject中读取JSONObject或者JSONArray
	* 支持读取所有基本类型，防止异常且支持默认值
* KeyboardUtils
	* 支持展开软键盘
	* 支持关闭软键盘
* NetUtils
	* 支持判断是否是wifi连接
	* 支持判断是否是GPRS连接
	* 支持判断是否是网络连接
	* 支持网络传输大数据时是否流量敏感
	* 支持获取当前网络状态，2G/3G/4G/WIFI
* ParseUtils
	* 支持字符串转化为short，支持默认值
	* 支持字符串转化为int，支持默认值
	* 支持字符串转化为long，支持默认值
	* 支持字符串转化为double，支持默认值
	* 支持字符串转化为float，支持默认值
	* 支持字符串转化为boolean，支持默认值
* ScreenUtils
	* 支持判断当前横竖屏
	* 支持测量整个界面
	* 支持判断是否有导航栏
	* 支持获取导航栏/状态栏高度
	* 支持获取屏幕可操作的宽度/高度，包括状态栏不包括导航栏
	* 支持获取屏幕真实的宽度/高度，包含状态栏和导航栏
	* 支持获取屏幕的物理尺寸，以英寸为单位
	* 支持获取当前屏幕截图，支持包含状态栏

	
	
	


	
	