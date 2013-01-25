// Client.cpp
#include <fstream.h>

#include <OB/CORBA.h>
#include <OB/OBORB.h>

// Include 由IDL生成的文件
#include <java/lang/Exception.h>
#include <java/lang/Throwable.h>
#include <javax/ejb/CreateException.h>
#include <javax/ejb/RemoveException.h>
#include <ejbinterop/Logger.h>
#include <ejbinterop/LoggerHome.h>
//为LoggerHome对象提供一个ORB和一个corbaname URL，
//在服务器上记录一个简单的字符串信息

void
run(CORBA::ORB_ptr orb, const char* logger_home_url)
{
  cout << "Looking for: " << logger_home_url << endl;

  //在名字上下文中查询由corbaname URL指向的LoggerHome对象
  CORBA::Object_var home_obj
    = orb->string_to_object(logger_home_url);

  //执行一个安全的downcast
  ejbinterop::LoggerHome_var home
    = ejbinterop::LoggerHome::_narrow(home_obj.in());

  assert(!CORBA::is_nil(home));

  // 创建一个Logger EJB的引用
  ejbinterop::Logger_var logger = home->create();

  CORBA::WStringValue_var msg =
    new CORBA::WStringValue((const CORBA::WChar*)L"Message 
      from a C++ client");
  
  cout << "Logging..." << endl;

  //记录我们的信息
  logger->logString(msg);

  // 告诉应用服务器，我们将在不是使用这个引用
  logger->remove();

  cout << "Done" << endl;
}
//简单的main函数，检验参数，创建一个ORB同时处理异常
int
main(int argc, char* argv[])
{
  int exit_code = 0;
  CORBA::ORB_var orb;

  try {

  //检测参数
  if (argc != 2) {
    cerr << "Usage: Client <corbaname URL of LoggerHome>" << endl;
    return 1;
  }

  //创建一个 ORB
  orb = CORBA::ORB_init(argc, argv);

  //注册factories

  
  CORBA::ValueFactory factory = new java::lang::Throwable_init;
  orb -> register_value_factory(java::lang::Throwable::_OB_id(),
    factory);
  factory -> _remove_ref();

  factory = new java::lang::Exception_init;
  orb -> register_value_factory(java::lang::Exception::_OB_id(),
    factory);
  factory -> _remove_ref();

  factory = new javax::ejb::CreateException_init;
  orb -> 		    register_value_factory(javax::ejb::CreateException::_OB_id(),
      factory);
  factory -> _remove_ref();

  factory = new javax::ejb::RemoveException_init;
  orb ->
    register_value_factory(javax::ejb::RemoveException::_OB_id(),
      factory);
  factory -> _remove_ref();

  //执行这个程序
  run(orb, argv[1]);

} catch(const CORBA::Exception& ex) {
  // 处理任何CORBA相关的异常
  cerr << ex._to_string() << endl;
  exit_code = 1;
}

  // 释放任何ORB资源
  if (!CORBA::is_nil(orb)) {
    try {
      orb -> destroy();
    } catch(const CORBA::Exception& ex) {
      cerr << ex._to_string() << endl;
      exit_code = 1;
    }
  }

  return exit_code;
}