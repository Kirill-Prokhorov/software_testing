package ru.stqa.software_testing.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {

  private final Set<GroupData> delegate;

  public Groups(Groups groups) {

    this.delegate = new HashSet<>(groups.delegate);
  }

  public Groups() {
    this.delegate = new HashSet<>();
  }

  public Groups(Collection<GroupData> groups) {

    this.delegate = new HashSet<>(groups);

  }

  @Override
  protected Set<GroupData> delegate() {
    return delegate;
  }

  public Groups withAdded(GroupData group){

    Groups groups = new Groups(this);
    groups.add(group);
    return groups;
  }
  public Groups withOut(GroupData group){

    Groups groups = new Groups(this);
    groups.remove(group);
    return groups;
  }

}
