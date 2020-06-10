package com.example.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.task.serviceImpl.TaskInvokeService;

@Component
public class EventMonitor {

	@Autowired
	private TaskInvokeService taskInvokeService;

	String path = "C:\\Users\\Ritesht\\Downloads";
	Path myDir;

	@PostConstruct
	private void init() {
		this.eventMonitor();
	}

	public EventMonitor() {
		File dir = new File(path);
		myDir = dir.toPath();
		try {
			Boolean isFolder = (Boolean) Files.getAttribute(myDir, "basic:isDirectory", LinkOption.NOFOLLOW_LINKS);
			if (!isFolder) {
				throw new IllegalArgumentException("Path: " + myDir + " is not a folder");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Watching path: " + myDir);
	}

	void eventMonitor() {
		for (;;) {
			try {
				WatchService watcher = myDir.getFileSystem().newWatchService();
				myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);

				WatchKey watckKey = watcher.take();

				List<WatchEvent<?>> events = watckKey.pollEvents();
				System.out.println(" ## 1 ##");

				System.out.println("Task service will be invoked by folder change request.....");
				for (WatchEvent<?> event : events) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("Created: " + event.kind().toString());
					}
					if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
						System.out.println("Delete: " + event.context().toString());
					}
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("Modify: " + event.context().toString());
					}
				}
				if (events.size() > 0) {
					taskInvokeService.executeTask("myjob");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}