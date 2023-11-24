
node('linux')
{
  stage ('Poll') {
               // Poll from upstream:
               checkout([
                      $class: 'GitSCM': 'GitSCM',
                       branches: [[name: '*/master']],
                       doGenerateSubmoduleConfigurations: false,
                       extensions: [],
                       userRemoteConfigs: [[url: 'https://git.savannah.gnu.org/git/groff.git']]])

                // Poll for local changes
                checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/main']],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        userRemoteConfigs: [[url: "https://github.com/ZOSOpenTools/groffport.git"]]])
  }
  stage('Build') {
    build job: 'Port-Pipeline', parameters: 
      [string(name: 'PORT_GITHUB_REPO', value: 'https://github.com/ZOSOpenTools/groffport.git'), 
       string(name: 'PORT_DESCRIPTION', value: 'Typesetting library'),
       string(name: 'BUILD_LINE', value: 'DEV')
      ]
  }
}

