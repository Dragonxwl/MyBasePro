# MyBasePro
简书地址 https://www.jianshu.com/u/342a376179c3
    DToast封装 https://www.jianshu.com/p/5a357292554e
    retrofit2 + okhttp3 + rxjava2 实现简单网络请求 https://www.jianshu.com/p/a36a51166fb2

base 基类
    Aplication
    BaseActivity
        Activity基类 继承RxAppCompatActivity 绑定Activity生命周期 网络请求需要
    BaseBean
        接口返回结构体基类
bean 数据结构体
config 配置文件
    ACConfig SP保存配置文件
    CustomerConfig 客户端配置文件
demo 示例
    okhttpDemoActivity okhttp 请求示例
main
    MainActivity 启动页
okhttp okhttp相关文件
    ApiService 定义请求接口
    BaseObserver 接口请求返回结果 分类 观察者模式
    ErrorCode 自定义接口错误码
    HeaderInterceptor 自定义请求头拦截器
    RetrofitClient Retrofit类
    RxSchedulers RxJava
utils 工具类
    GsonUtil Gson与String转换
    LogUtil 日志工具
    SharePreferenceDataUtil SP保存工具类
    StringUtils 获取常用字符串工具类
    ToastUtil DToast封装工具类

第三方库Fork地址
DToast https://github.com/Dragonxwl/DToast
