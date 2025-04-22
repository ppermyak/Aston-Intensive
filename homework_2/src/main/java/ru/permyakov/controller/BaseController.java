package ru.permyakov.controller;

import java.util.Scanner;

public interface BaseController {
    void startAction(Scanner scanner);
    String nextView();
}
