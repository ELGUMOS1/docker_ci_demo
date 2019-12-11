node {
   try {
     stage('Clone') {
       checkout([
          $class: 'GitSCM',
          branch: 'master',
          userRemoteConfigs: [[
                credentialsId: 'ebc0533e-db18-45d5-b041-4ca58fb25b15',
                url: 'https://github.com/cloudboxlabs/blog-code.git'
          ]]
       ])
     }
     stage('Integration Test') {
       sh "rm /usr/local/bin/docker-compose"
       sh "curl -L https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-`uname -s`-`uname -m` > docker-compose" 
       sh "chmod +x docker-compose"
       sh "mv docker-compose /usr/local/bin" 
       sh "/usr/local/bin/docker-compose -f docker-ci-demo/docker-compose-ci-test.yaml up -d"
       sh "/usr/bin/docker wait docker-ci-demo_integration_test_1"
     }
     stage('Deploy') {
       sh "cd docker-ci-demo && /usr/bin/docker build ."
     }
   } catch (e) {
     currentBuild.result = 'FAILURE'
     throw e
   } finally {
       sh "/usr/local/bin/docker-compose -f docker-ci-demo/docker-compose-ci-test.yaml down"
   }
}
