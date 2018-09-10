pipeline {
  agent any
  stages {
    stage('Pull Staging') {
      when {
        anyOf {
          branch 'apricot';
          branch 'carrot';
          branch 'banana'
        }
      }
      steps {
        sh 'git fetch origin staging:staging'
        sh 'git merge staging'
      }
    }
    stage('Setup Gradle') {
      steps {
        sh 'chmod +x gradlew'
      }
    }
    stage('Build Debug APK') {
      when {
        not {
          branch 'master'
        }
      }
      steps {
        script {
          if (env.BRANCH_NAME == 'apricot') {
            sh './gradlew assembleApricot --configure-on-demand --daemon'
          } else if (env.BRANCH_NAME == 'carrot') {
            sh './gradlew assembleCarrot --configure-on-demand --daemon'
          } else if (env.BRANCH_NAME == 'banana') {
            sh './gradlew assembleBanana --configure-on-demand --daemon'
          } else {
            sh './gradlew assembleDebug --configure-on-demand --daemon'
          }
        }

        archiveArtifacts '**/*.apk'
      }
      post {
        aborted {
          slackSend color: 'warning', message: "DEBUG BUILD ABORTED: ${env.RUN_DISPLAY_URL}"
        }
        unstable {
          slackSend color: 'warning', message: "DEBUG BUILD UNSTABLE: ${env.RUN_DISPLAY_URL}"
        }
        failure {
          slackSend color: 'danger', message: "DEBUG BUILD FAILED: ${env.RUN_DISPLAY_URL}"
        }
        success {
          slackSend color: 'good', message: "DEBUG BUILD SUCCESSFUL: \n `${env.RUN_DISPLAY_URL}` \n DEBUG APK: \n `${env.BUILD_URL}artifact/app/build/outputs/apk/debug/app-debug.apk`"
        }
      }
    }
    stage('Build Release APK') {
      when {
        branch 'master'
      }
      steps {
        sh './gradlew assembleRelease --configure-on-demand --daemon'

        archiveArtifacts '**/*.apk'

        signAndroidApks keyStoreId: 'personal-keystore', keyAlias: 'myplaystorekey', apksToSign: '**/*-unsigned.apk'
      }
      post {
        aborted {
          slackSend color: 'warning', message: "RELEASE BUILD ABORTED: ${env.RUN_DISPLAY_URL}"
        }
        unstable {
          slackSend color: 'warning', message: "RELEASE BUILD UNSTABLE: ${env.RUN_DISPLAY_URL}"
        }
        failure {
          slackSend color: 'danger', message: "RELEASE BUILD FAILED: ${env.RUN_DISPLAY_URL}"
        }
        success {
          slackSend color: 'good', message: "RELEASE BUILD SUCCESSFUL: \n `${env.RUN_DISPLAY_URL}` \n RELEASE APK: \n `${env.BUILD_URL}artifact/SignApksBuilder-out/personal-keystore/myplaystorekey/app-release-unsigned.apk/app-release.apk`"
        }
      }
    }
    stage('Deploy to Alpha') {
      when {
        branch 'master'
      }
      steps {
        androidApkUpload googleCredentialsId: 'google-play-publishing', apkFilesPattern: '**/*-release.apk', trackName: 'alpha'
      }
      post {
        aborted {
          slackSend color: 'warning', message: "ALPHA RELEASE ABORTED: \n `${env.RUN_DISPLAY_URL}`"
        }
        unstable {
          slackSend color: 'warning', message: "ALPHA RELEASE UNSTABLE: \n `${env.RUN_DISPLAY_URL}`"
        }
        failure {
          slackSend color: 'danger', message: "ALPHA RELEASE FAILED: \n `${env.RUN_DISPLA2Y_URL}`"
        }
        success {
          slackSend color: 'good', message: "ALPHA RELEASE SUCCESSFUL: \n `${env.RUN_DISPLAY_URL}`"
        }
      }
    }
  }
}