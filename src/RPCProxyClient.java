import java.lang.reflect.Method;

public class RPCProxyClient implements java.lang.reflect.InvocationHandler{
    private Object obj;

    public RPCProxyClient(Object obj){
        this.obj=obj;
    }

    /**
     * �õ����������;
     */
    public static Object getProxy(Object obj){
        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), new RPCProxyClient(obj));
    }

    /**
     * ���ô˷���ִ��
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //�������;
        Object result = new Object();
        // ...ִ��ͨ������߼�
        // ...
        return result;
    }
    
    public static void main(String[] args) {
         HelloWorldService helloWorldService = (HelloWorldService)RPCProxyClient.getProxy(HelloWorldService.class);
         helloWorldService.sayHello("test");
     }
}