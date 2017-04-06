##Introduction

	This project is an extension of mybatis by using dynamic executor bound with current thread 
	to solve 'Cannot change the ExecutorType when there is an existing transaction' 
	for mybatis-spring project.

##Example

1、Simple usage as follows (No Spring integration) : 

	/**
	 * returned DefaultExSqlSessionFactory as default
	 */
	public SqlSessionFactory getSqlSessionFactory() {
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
		return new ExSqlSessionFactoryBuilder().build(inputStream);
	}
	
	public void testNormalInsertAndBatchInsert() throws Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			//1、No-batch execution mode for insert
			for(int i = 1; i <= 5; i++){
				Product product = new Product(...);
				int affectedRow = sqlSession.insert(getMapperKey(Product.class, "insertProduct"), product);
				System.out.println(">>> inserted rows " + affectedRow);
			}
			
			//2、batch execution mode for insert by using BatchTemplate
			BatchTemplate batchTemplate = new BatchTemplate(sqlSession);
			List<Integer> batchResultList = batchTemplate.execute(new SqlSessionCallback<List<Integer>>(){
				public List<Integer> doInSqlSession(SqlSession sqlSession) {
					List<Integer> list = new ArrayList<Integer>();
					for(int i = 6; i <= 10; i++){
						Product product = new Product(...);
						sqlSession.insert(getMapperKey(Product.class, "insertProduct"), product);
					}
					BatchResultUtils.extractBatchResult(list, sqlSession.flushStatements());
					return list;
				}
			});
			System.out.println(">>> batch inserted rows " + batchResultList.size());
			sqlSession.commit();
		} catch(Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
	public void testInsertOrder() throws Exception {
		MainOrder mainOrder = new MainOrder(...);
		List<ChildOrder> childOrderList = new ArrayList<ChildOrder>();
		for(int i = 1; i <= 5; i++){
			ChildOrder childOrder = new ChildOrder(...);
			childOrderList.add(childOrder);
		}
		ExSqlSession sqlSession = null;
		try {
			sqlSession = (ExSqlSession) getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			//1、No-batch execution mode for insert
			int affectedRow = sqlSession.insert(getMapperKey(MainOrder.class, "insertMainOrder"), mainOrder);
			System.out.println(">>> inserted rows " + affectedRow);
			
			//2、batch execution mode for insert by using extension method
			int[] affectedRows = sqlSession.batchInsert(getMapperKey(ChildOrder.class, "insertChildOrder"), childOrderList);
			System.out.println(">>> inserted rows " + affectedRows.length);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
2、Simple usage as follows (With Spring integration) : 
	
	2.1、Spring applicationContext.xml :
	
	<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
		...
	</bean>
	
	<bean id="jdbcTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <tx:annotation-driven transaction-manager="jdbcTransactionManager" proxy-target-class="true"/>

    <bean id="sqlSessionFactory" class="com.penglecode.mybatis.ex.spring.SimpleExSqlSessionFactoryBean">
        <property name="configLocation" value="classpath:mybatis/spring/mybatis-config.xml"/>
        <property name="dataSource" ref="dataSource"/>
    </bean>

	<bean id="sqlSessionTemplate" class="com.penglecode.mybatis.ex.spring.ExSqlSessionTemplate">
		<constructor-arg index="0" ref="sqlSessionFactory"/>
		<constructor-arg index="1" value="SIMPLE"/>
	</bean>
	
	2.1、Java code: 
	
	/**
	 * sqlSessionTemplate actual type is ExSqlSessionTemplate
	 */
	@Resource(name="sqlSessionTemplate")
	private ExSqlSession sqlSessionTemplate;
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void testInsertOrder() throws Exception {
		MainOrder mainOrder = new MainOrder(...);
		List<ChildOrder> childOrderList = new ArrayList<ChildOrder>();
		for(int i = 1; i <= 5; i++){
			ChildOrder childOrder = new ChildOrder(...);
			childOrderList.add(childOrder);
		}
		//1、No-batch execution mode for insert
		int affectedRow = sqlSessionTemplate.insert(getMapperKey(MainOrder.class, "insertMainOrder"), mainOrder);
		System.out.println(">>> inserted rows " + affectedRow);
		//2、batch execution mode for insert by using extension method
		int[] affectedRows = sqlSessionTemplate.batchInsert(getMapperKey(ChildOrder.class, "insertChildOrder"), childOrderList);
		System.out.println(">>> inserted rows " + affectedRows.length);
	}
	