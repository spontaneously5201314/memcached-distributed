/**
 * Created by myg on 2016/9/6.
 */
package com.spon;
/*
*  1.实现分布式--需要考虑找节点，还有容错，还有备份，存数据
*  2.实现接口（包装所有的操作，通过一个工厂或者配置文件指定）
*  3.可能会需要dom操作
*  4.cache中放的是一些缓存接口，constants中放的是一个常量，memcached中放的是memcached的实现，
*       如果有redis的实现，就新建一个redis的包。utils中放一些工具
*  5.在每一种实现下面需要一个连接池管理操作，对应在servlet下面需要一个servlet，可以用来查看当
*       前管理的连接，以及状态，如果需要，还可以加上查询数据的操作
*  6.是否需要设置一个文件对所有的连接的生命周期进行管理？
* */