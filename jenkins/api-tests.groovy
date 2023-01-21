timeout(60) {
    node("maven-slave") {
        stage("Checkout") {
            checkout scm
        }
        stage("Run tests") {
            def exitCode = sh(
                    returnStatus: true,
                    script: """
                    mvn test -DbaseUrl=$API_URL -Durl=$SUB_URL -DuserStatus=$USER_STATUS -Demail=$EMAIL -Did=$ID -DfirstName=$FIRST_NAME -DlastName=$LAST_NAME -Dphone=$PHONE -Dpasssword=$PASSWORD -Dusername=$USERNAME
                    """
            )

            if(exitCode == 1) {
                currentBuild.result = 'UNSTABLE'
            }
        }
        stage("Publish allure results") {
            allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'allure-results']]
            ])
        }
    }
}