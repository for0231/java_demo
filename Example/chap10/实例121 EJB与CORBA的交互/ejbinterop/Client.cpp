// Client.cpp
#include <fstream.h>

#include <OB/CORBA.h>
#include <OB/OBORB.h>

// Include ��IDL���ɵ��ļ�
#include <java/lang/Exception.h>
#include <java/lang/Throwable.h>
#include <javax/ejb/CreateException.h>
#include <javax/ejb/RemoveException.h>
#include <ejbinterop/Logger.h>
#include <ejbinterop/LoggerHome.h>
//ΪLoggerHome�����ṩһ��ORB��һ��corbaname URL��
//�ڷ������ϼ�¼һ���򵥵��ַ�����Ϣ

void
run(CORBA::ORB_ptr orb, const char* logger_home_url)
{
  cout << "Looking for: " << logger_home_url << endl;

  //�������������в�ѯ��corbaname URLָ���LoggerHome����
  CORBA::Object_var home_obj
    = orb->string_to_object(logger_home_url);

  //ִ��һ����ȫ��downcast
  ejbinterop::LoggerHome_var home
    = ejbinterop::LoggerHome::_narrow(home_obj.in());

  assert(!CORBA::is_nil(home));

  // ����һ��Logger EJB������
  ejbinterop::Logger_var logger = home->create();

  CORBA::WStringValue_var msg =
    new CORBA::WStringValue((const CORBA::WChar*)L"Message 
      from a C++ client");
  
  cout << "Logging..." << endl;

  //��¼���ǵ���Ϣ
  logger->logString(msg);

  // ����Ӧ�÷����������ǽ��ڲ���ʹ���������
  logger->remove();

  cout << "Done" << endl;
}
//�򵥵�main�������������������һ��ORBͬʱ�����쳣
int
main(int argc, char* argv[])
{
  int exit_code = 0;
  CORBA::ORB_var orb;

  try {

  //������
  if (argc != 2) {
    cerr << "Usage: Client <corbaname URL of LoggerHome>" << endl;
    return 1;
  }

  //����һ�� ORB
  orb = CORBA::ORB_init(argc, argv);

  //ע��factories

  
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

  //ִ���������
  run(orb, argv[1]);

} catch(const CORBA::Exception& ex) {
  // �����κ�CORBA��ص��쳣
  cerr << ex._to_string() << endl;
  exit_code = 1;
}

  // �ͷ��κ�ORB��Դ
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