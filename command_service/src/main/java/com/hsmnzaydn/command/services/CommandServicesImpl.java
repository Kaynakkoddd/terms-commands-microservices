package com.hsmnzaydn.command.services;


import com.hsmnzaydn.command.model.CommandDTO;
import com.hsmnzaydn.command.repository.CommandRepository;
import com.hsmnzaydn.common.events.CreateCommandEvent;
import com.hsmnzaydn.core_entity_lib.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandServicesImpl implements CommandServices {


    private final CommandRepository commandRepository;




    @Override
    public CommandDTO createCommand(CommandDTO commandDTO) {
        Command command = new Command();
        command.setCommandTitle(commandDTO.getCommandTitle());
        command.setCategoryId(commandDTO.getCategoryId());
        command = commandRepository.save(command);
        CreateCommandEvent createCommandEvent = new CreateCommandEvent();
        createCommandEvent.setCommandId(command.getId());
        createCommandEvent.setCategoryId(command.getCategoryId());
        return commandDTO;
    }

    @Override
    public List<CommandDTO> getCommandsOfCategory(int categoryId) {

       return commandRepository.findByCategoryId(categoryId).stream()
                .map(command -> new CommandDTO(command.getCategoryId(), command.getCommandTitle()))
                .collect(Collectors.toList());

    }


}
