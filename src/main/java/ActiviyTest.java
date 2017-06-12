import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;


public class ActiviyTest {

	public static void main(String[] argStrings ){
		/**
		 * @author 邹龙平
		 * @description 
		 *     根据配置文件生成在数据库中生成与工作流相关的23张表，其中有5类表，以实现相应的功能，
		 *     在业务代码中借由processEngine对象来实现对工作流的操作
		 */
		ProcessEngine processEngine = ProcessEngineConfiguration.
		createProcessEngineConfigurationFromResource("config/activi.cfg.xml").buildProcessEngine();
		
		/**
		 * 将流程定义（用eclipse插件勾画的流程图）进行发布
		 */
		Deployment deploy = processEngine.getRepositoryService()
		.createDeployment()
		.name("入门程序").addClasspathResource("diagrams/helloActiviti.bpmn")
		.deploy();
		
		System.out.println( deploy.getName()+deploy.getId()+"流程定已经成功部署");
		
		/**
		 * 获取流程实例
		 */
		String processDentifiedId="hellowActiviti";
		
		ProcessInstance startProcessInstance =
				processEngine.getRuntimeService().startProcessInstanceByKey(processDentifiedId);
		
	}
	/**
	 * 查询当前人的个人任务
	 */
	public void findMyPersonTask(ProcessEngine processEngine,String personString){
		/**
		 * 获取流程引擎中的taskService,通过查询条件来查询出对应的任务，如任务执行者
		 */
		List<Task> list = processEngine.getTaskService()//与正在执行任务管理相关的service
		.createTaskQuery()//创建任务查询对象
		.taskAssignee(personString)
		.list();
		if (list.size()>0) {
			//遍历出taskList中的Task对象属性
			for (Task task : list) {
				System.out.println("任务执行人："+task.getAssignee());
				System.out.println("任务ID:"+task.getId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
				System.out.println("流程实例ID："+task.getProcessInstanceId());
				System.out.println("任务执行时间："+task);
				System.out.println("################################");
			}
		}
		
	}
	
	
	/***
	 * 完成对应taskId的任务，借由taskService来执行操作
	 */
	public void completeTaskById(ProcessEngine processEngine,String taskId){
		processEngine.getTaskService().complete(taskId);
		System.out.println("完成的任务ID"+taskId);
	}
	
	/**
	 * 流程定义
	 */
	//查询流程定义
	public void findProcessDentified(){
		ProcessEngine processEngine = 
				ProcessEngineConfiguration.
				createProcessEngineConfigurationFromResource("config/activi.cfg.xml").buildProcessEngine();
		//查询出所有的流程定义
		List<ProcessDefinition> list = 
				processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				 /** 指定查询条件，where条件 */
				//.deploymentId(deploymentId) 使用部署对象ID进行查询
				//.processDefinitionId(processDefinitionId)通过流程定义ID查询
				 // .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
				
				/**结果集排序方式**/
				.orderByProcessDefinitionVersion().asc().list();
		 // .singleResult();//返回惟一结果集
		 // .count();//返回结果集数量
        // .listPage(firstResult, maxResults);//分页查询
	}
	
   //测试一下修改后.提交至github
	public void printConsolse(){
		System.out.println("调教！");
	}
}
