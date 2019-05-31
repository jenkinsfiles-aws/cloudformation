#!groovy

pipeline {

  agent any

  environment {
    CFN_TMP = "cfn.sh"
  }

  parameters {
    string(
      name: 'EXTRA_ARGS',
      defaultValue: '',
      description: 'CloudFormation extra arguments'
    )
    string(
      name: 'GIT_BRANCHES_CFN',
      defaultValue: '*/master',
      description: "Git branch or tag name or commit id to retrieve of GIT_URL to retrieve CloudFormation template file"
    )
    string(
      name: 'GIT_URL',
      defaultValue: 'https://github.com/aws-cloudformation-tm/vpc.git',
      description: "GitHub URL to retrieve CloudFormation template"
    )
    string(
      name: 'REGION',
      defaultValue: '',
      description: 'CloudFormation region'
    )
    string(
      name: 'STACK_NAME',
      defaultValue: '',
      description: 'CloudFormation stack name'
    )
    string(
      name: 'TEMPLATE_FILE_PATH',
      defaultValue: '',
      description: 'CloudFormation template file path'
    )
    string(
      name: 'WORKING_DIR',
      defaultValue: 'cfn',
      description: 'Job working directory'
    )
  }

  stages {
    stage('Initialize wokring directory') {
      steps {
        dir ("${params.WORKING_DIR}") {
          cleanWs()
        }
      }
    }

    stage('Retrieve CloudFormation template file from Github') {
      steps {
        checkout(
          [
            $class: 'GitSCM',
            branches: [
              [
                name: "${params.GIT_BRANCHES_CFN}"
              ]
            ],
            extensions: [
              [
                $class: 'RelativeTargetDirectory',
                relativeTargetDir: "${params.WORKING_DIR}"
              ]
            ],
            doGenerateSubmoduleConfigurations: false,
            submoduleCfg: [],
            userRemoteConfigs: [
              [
                url: "${params.GIT_URL}"
              ]
            ]
          ]
        )
      }
    }

    stage('Create Stack') {
      steps {
        dir ("${params.WORKING_DIR}") {
           sh "aws cloudformation create-stack --stack-name ${params.STACK_NAME} --template-body file://${params.TEMPLATE_FILE_PATH} --region ${params.REGION} ${params.EXTRA_ARGS}"
           sh "aws cloudformation wait stack-create-complete --stack-name ${params.STACK_NAME} --region ${params.REGION} ${params.EXTRA_ARGS}"
           sh "aws cloudformation describe-stacks --stack-name ${params.STACK_NAME} --region ${params.REGION} ${params.EXTRA_ARGS}"
        }
      }
    }
  }
}
