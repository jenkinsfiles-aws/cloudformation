def upload() {
  import jenkins.model.*
  import com.cloudbees.plugins.credentials.*
  import com.cloudbees.plugins.credentials.domains.Domain
  import org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl
  import java.nio.file.*
  
  Path fileLocation = Paths.get("${env.ANSIBLE_INVENTORY_DIR}/${params.INVENTORY_FILE_NAME}")
  
  def secretBytes = SecretBytes.fromBytes(Files.readAllBytes(fileLocation))
  def credentials = new FileCredentialsImpl(CredentialsScope.GLOBAL, 'my test file', 'description', 'file.txt', secretBytes)
  
  SystemCredentialsProvider.instance.store.addCredentials(Domain.global(), credentials)
}
return this
