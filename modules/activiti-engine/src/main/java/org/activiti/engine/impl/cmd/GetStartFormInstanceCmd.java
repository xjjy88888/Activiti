/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.impl.cmd;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.form.StartFormInstance;
import org.activiti.engine.impl.cfg.RepositorySession;
import org.activiti.engine.impl.form.StartFormHandler;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.repository.ProcessDefinitionEntity;


/**
 * @author Tom Baeyens
 */
public class GetStartFormInstanceCmd implements Command<StartFormInstance> {

  protected String processDefinitionId;

  public GetStartFormInstanceCmd(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public StartFormInstance execute(CommandContext commandContext) {
    RepositorySession repositorySession = commandContext.getRepositorySession();
    ProcessDefinitionEntity processDefinition = repositorySession.findDeployedProcessDefinitionById(processDefinitionId);
    if (processDefinition == null) {
      throw new ActivitiException("No process definition found for id '" + processDefinitionId +"'");
    }
    
    StartFormHandler startFormHandler = processDefinition.getStartFormHandler();
    if (startFormHandler == null) {
      throw new ActivitiException("No startFormHandler defined in process '" + processDefinitionId +"'");
    }
    
    
    return startFormHandler.createStartFormInstance(processDefinition);
  }
}
