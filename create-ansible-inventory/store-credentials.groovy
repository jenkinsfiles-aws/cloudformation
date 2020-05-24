def upload() {
  import jenkins.model.*
  import com.cloudbees.plugins.credentials.*
  import com.cloudbees.plugins.credentials.common.*
  import com.cloudbees.plugins.credentials.domains.*
  import com.cloudbees.plugins.credentials.impl.*
  import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
  import org.jenkinsci.plugins.plaincredentials.*
  import org.jenkinsci.plugins.plaincredentials.impl.*
  import hudson.util.Secret
  import hudson.plugins.sshslaves.*
  import org.apache.commons.fileupload.* 
  import org.apache.commons.fileupload.disk.*
  import java.nio.file.Files
  
  Path fileLocation = Paths.get("${env.ANSIBLE_INVENTORY_DIR}/${params.INVENTORY_FILE_NAME}")
  
  def secretBytes = SecretBytes.fromBytes(Files.readAllBytes(fileLocation))
  def credentials = new FileCredentialsImpl(CredentialsScope.GLOBAL, 'my test file', 'description', 'file.txt', secretBytes)
  
  SystemCredentialsProvider.instance.store.addCredentials(Domain.global(), credentials)
}
return this
