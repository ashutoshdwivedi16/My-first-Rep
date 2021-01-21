import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    private static final String baseDir = ".";
    private static final String SPRING_BOOT_GROUP_ID = "org.springframework.boot";


    public static void main(String[] args) throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();

        Model model;
        if ((new File("pom.xml")).exists())
            model = reader.read(new FileReader("pom.xml"));
        else
            model = reader.read(
                    new InputStreamReader(
                            Main.class.getResourceAsStream(
                                    "/META-INF/maven/de.scrum-master.stackoverflow/aspectj-introduce-method/pom.xml"
                            )
                    )
            );
        System.out.println(model.getId());
        System.out.println(model.getGroupId());
        System.out.println(model.getArtifactId());
        System.out.println(model.getVersion());
        System.out.println(model.getDependencies());


        boolean springBootPresent;
        List<Dependency> dependecies = model.getDependencies();
        Predicate<Dependency> springBoot = dep -> dep.getGroupId().equals(SPRING_BOOT_GROUP_ID);
        springBootPresent = dependecies.stream().anyMatch(springBoot);

        System.out.println("Spring Boot Present " + springBootPresent);

        if (springBootPresent) {
            MavenXpp3Writer writer = new MavenXpp3Writer();
            model.setParent(addSpringBootParent());

           /*    <dependencies >
    <dependency >
      <groupId > org.apache.maven </groupId >
      <artifactId > maven - model </artifactId >
      <version > 3.3 .9 </version >
    </dependency >

      <dependency >
        <groupId > org.springframework.boot </groupId >
        <artifactId > spring - boot - starter </artifactId >
      </dependency >

      <dependency >
        <groupId > org.springframework.boot </groupId >
        <artifactId > spring - boot - starter - test </artifactId >
        <scope > test </scope >
      </dependency >
  </dependencies >*/
            List<Dependency> list = new ArrayList();

            list.add(addDependecy("org.apache.maven", "maven - model", "3.3 .9 "));
            addDependecy("org.springframework.boot", "spring - boot - starter", "3.3 .9 ");

            addDependecy("org.springframework.boot", "spring - boot - starter - test", "3.3 .9 ");


            List<Dependency> list = model.getDependencies();
            list.add(addDependecy());
            model.setDependencies(list);

            writer.write(new FileOutputStream(new File(baseDir, "/pomm.xml")), model);


        }

    }


    private static Parent addSpringBootParent() {
        Parent parent = new Parent();
        parent.setArtifactId("spring-boot-starter-parent");
        parent.setGroupId("org.springframework.boot");
        parent.setRelativePath(null);
        parent.setVersion("2.1.7.RELEASE");

        return parent;
    }


    private static Dependency addDependecy(String groupId, String artifactId, String version) {

        Dependency dependency = new Dependency();
        dependency.setGroupId("org.apache.maven");
        dependency.setArtifactId("maven-modelrrr");
        dependency.setVersion("3.3.9");

        return dependency;


    }
}


