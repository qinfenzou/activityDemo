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
		 * @author ����ƽ
		 * @description 
		 *     ���������ļ����������ݿ��������빤������ص�23�ű�������5�����ʵ����Ӧ�Ĺ��ܣ�
		 *     ��ҵ������н���processEngine������ʵ�ֶԹ������Ĳ���
		 */
		ProcessEngine processEngine = ProcessEngineConfiguration.
		createProcessEngineConfigurationFromResource("config/activi.cfg.xml").buildProcessEngine();
		
		/**
		 * �����̶��壨��eclipse�������������ͼ�����з���
		 */
		Deployment deploy = processEngine.getRepositoryService()
		.createDeployment()
		.name("���ų���").addClasspathResource("diagrams/helloActiviti.bpmn")
		.deploy();
		
		System.out.println( deploy.getName()+deploy.getId()+"���̶��Ѿ��ɹ�����");
		
		/**
		 * ��ȡ����ʵ��
		 */
		String processDentifiedId="hellowActiviti";
		
		ProcessInstance startProcessInstance =
				processEngine.getRuntimeService().startProcessInstanceByKey(processDentifiedId);
		
	}
	/**
	 * ��ѯ��ǰ�˵ĸ�������
	 */
	public void findMyPersonTask(ProcessEngine processEngine,String personString){
		/**
		 * ��ȡ���������е�taskService,ͨ����ѯ��������ѯ����Ӧ������������ִ����
		 */
		List<Task> list = processEngine.getTaskService()//������ִ�����������ص�service
		.createTaskQuery()//���������ѯ����
		.taskAssignee(personString)
		.list();
		if (list.size()>0) {
			//������taskList�е�Task��������
			for (Task task : list) {
				System.out.println("����ִ���ˣ�"+task.getAssignee());
				System.out.println("����ID:"+task.getId());
				System.out.println("���̶���ID:"+task.getProcessDefinitionId());
				System.out.println("����ʵ��ID��"+task.getProcessInstanceId());
				System.out.println("����ִ��ʱ�䣺"+task);
				System.out.println("################################");
			}
		}
		
	}
	
	
	/***
	 * ��ɶ�ӦtaskId�����񣬽���taskService��ִ�в���
	 */
	public void completeTaskById(ProcessEngine processEngine,String taskId){
		processEngine.getTaskService().complete(taskId);
		System.out.println("��ɵ�����ID"+taskId);
	}
	
	/**
	 * ���̶���
	 */
	//��ѯ���̶���
	public void findProcessDentified(){
		ProcessEngine processEngine = 
				ProcessEngineConfiguration.
				createProcessEngineConfigurationFromResource("config/activi.cfg.xml").buildProcessEngine();
		//��ѯ�����е����̶���
		List<ProcessDefinition> list = 
				processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				 /** ָ����ѯ������where���� */
				//.deploymentId(deploymentId) ʹ�ò������ID���в�ѯ
				//.processDefinitionId(processDefinitionId)ͨ�����̶���ID��ѯ
				 // .processDefinitionNameLike(processDefinitionNameLike)//ʹ�����̶��������ģ����ѯ
				
				/**���������ʽ**/
				.orderByProcessDefinitionVersion().asc().list();
		 // .singleResult();//����Ωһ�����
		 // .count();//���ؽ��������
        // .listPage(firstResult, maxResults);//��ҳ��ѯ
	}
	
   //����һ���޸ĺ�.�ύ��github
	public void printConsolse(){
		System.out.println("���̣�");
	}
}
