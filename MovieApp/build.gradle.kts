import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED

plugins {
    `common-java`
    alias(libs.plugins.springBoot)
    alias(libs.plugins.taskTree)
}

tasks.wrapper {
    gradleVersion = libs.versions.gradlew.get()
    distributionType = Wrapper.DistributionType.ALL
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.bundles.logging)
    implementation(libs.bundles.springBoot)

    runtimeOnly(libs.micrometerPrometheus)
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        getByName<JvmTestSuite>("test") {
            dependencies {
                compileOnly(libs.lombok)
                annotationProcessor(libs.lombok)
            }
            targets {
                all {
                    testTask.configure {
                        outputs.upToDateWhen { false }
                        reports.junitXml.outputLocation
                            .set(file("${layout.buildDirectory}/junit-reports"))
                        testLogging {
                            exceptionFormat = FULL
                            events = setOf(FAILED)
                            showStandardStreams = true
                        }
                    }
                }
            }
        }
        register<JvmTestSuite>("functionalTest") {
            testType.set(TestSuiteType.FUNCTIONAL_TEST)
            useJUnitJupiter(libs.versions.jupiter.get())
            extendTestConfigurations()
            dependencies {
                implementation.bundle(libs.bundles.functionalTest)
                runtimeOnly(libs.junit.vintageEngine)
            }
            targets {
                all {
                    testTask.configure {
                        jvmArgs = listOf("-XX:+EnableDynamicAgentLoading")
                        outputs.upToDateWhen { false }
                        testLogging {
                            exceptionFormat = FULL
                            events = setOf(FAILED)
                            showStandardStreams = true
                        }
                    }
                }
            }
        }
    }
}

@Suppress("UnstableApiUsage")
fun JvmTestSuite.extendTestConfigurations(): JvmTestSuite = apply {
    configurations["${name}Implementation"]
        .extendsFrom(configurations.testImplementation.get())
    configurations["${name}CompileOnly"]
        .extendsFrom(configurations.testCompileOnly.get())
    configurations["${name}AnnotationProcessor"]
        .extendsFrom(configurations.testAnnotationProcessor.get())
    configurations["${name}RuntimeOnly"]
        .extendsFrom(configurations.testRuntimeOnly.get())
}