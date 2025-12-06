`mkdir -p bin`
`javac -d bin $(find src -name "*.java")`
`jar cvef main.java.com.example.laba.Lab1 Laba1.jar -C bin .`
