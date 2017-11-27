package yummylau.modulea;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mockit.Mocked;

/**
 * 每一个单元测试类仅会在开始后执行BeforeClass，结束前执行AfterClass
 * 每一个单元测试方法Test前都会执行Before，Test后执行After
 * Created by g8931 on 2017/11/23.
 */

public class CalculaterTest {

    @Before
    public void setUp() {
        System.out.println("Before");
    }

    @After
    public void tearDown() {
        System.out.println("After");
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("BeforeClass");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.out.println("AfterClass");
    }


    @Test
    public void testAdd2() {
        Calculater calculater = new Calculater();
        int a = 2;
        int b = 3;
        int result = calculater.add(a, b);
        Assert.assertEquals(result, 5);
        System.out.println("testAdd2");
    }

    @Test
    public void testAdd() {
        Calculater calculater = new Calculater();
        int a = 1;
        int b = 2;
        int result = calculater.add(a, b);
        Assert.assertEquals(result, 3);
        System.out.println("testAdd");
    }

    @Test
    public void testAdd3(@Mocked Calculater calculater) {
        int a = 1;
        int b = 2;
        int result = calculater.add(a, b);
        Assert.assertEquals(result, 3);
        System.out.println("testAdd");
    }

}
