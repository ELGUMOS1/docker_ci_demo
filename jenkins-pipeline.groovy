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
       sh "curl -L https://github.com/docker/compose/releases/download/1.21.3/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose"
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
