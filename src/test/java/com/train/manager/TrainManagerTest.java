package com.train.manager;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.train.manager.exception.TrainManagerException;
import com.train.manager.exception.TrainManagerExceptionCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrainManagerTest {

    private final static String testResourcesPath = Paths.get("src", "test", "resources").toAbsolutePath().toString() + File.separator;

    @Test
    public void testTrainManager() {
        TrainManager.main(new String[]{testResourcesPath + "junit_input1.txt", testResourcesPath + "junit_app_config.json"});
    }

    @Test
    public void testForInvalidArguments() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{});
                });
        assertEquals(TrainManagerExceptionCode.TME_101, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidConfigFilePath() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input1.txt", testResourcesPath + "junit_app_invalid_config.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_102, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidConfigFileContent() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input1.txt", testResourcesPath + "junit_app_invalid_content_config.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_110, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidInputFilePath() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input_invalid.txt", testResourcesPath + "junit_app_config.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_104, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidTrainJourney() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input_invalid_journey.txt", testResourcesPath + "junit_app_config.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_106, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidTrainPath() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input1.txt", testResourcesPath + "junit_app_config_invalid_path.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_107, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidTrainMergerStation() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input1.txt", testResourcesPath + "junit_app_config_invalid_merger.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_108, exception.getTrainManagerExceptionCode());
    }

    @Test
    public void testForInvalidTrainRoute() {
        TrainManagerException exception =
                assertThrows(TrainManagerException.class, () -> {
                    TrainManager.main(new String[]{testResourcesPath + "junit_input1.txt", testResourcesPath + "junit_app_config_invalid_route.json"});
                });
        assertEquals(TrainManagerExceptionCode.TME_109, exception.getTrainManagerExceptionCode());
    }
}