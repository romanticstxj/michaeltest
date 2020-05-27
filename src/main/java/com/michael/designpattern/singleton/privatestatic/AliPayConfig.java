package com.michael.designpattern.singleton.privatestatic;

/**
 * 配置公共参数
 * 创建者 科帮网
 * 创建时间	2017年7月27日
 */
public final class AliPayConfig {
	
	 /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    public AliPayConfig(){};
    
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
        /**
         * 静态初始化器，获得的这个alipayClient只是单例，如果其不可变，那就是线程安全的，反之则线程不安全
         */
		private  static AlipayClient alipayClient = new AlipayClient();
		
		static {
			System.out.println("SingletonHolder static initializer");
		}
    }
    /**
     * 支付宝APP请求客户端实例
     * @Author  科帮网
     * @return  AlipayClient
     * @Date	2017年7月27日
     * 更新日志
     * 2017年7月27日  科帮网 首次创建
     *
     */
    public static AlipayClient getAlipayClient(){
        return SingletonHolder.alipayClient;
    }
}
