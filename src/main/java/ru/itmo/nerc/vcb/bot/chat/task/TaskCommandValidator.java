package ru.itmo.nerc.vcb.bot.chat.task;

import lombok.experimental.UtilityClass;
import ru.itmo.nerc.vcb.bot.chat.CommandProcessingException;
import ru.itmo.nerc.vcb.bot.chat.InlineQueryProcessor.ParsedQuery;
import ru.itmo.nerc.vcb.bot.user.UserContext;

@UtilityClass
public class TaskCommandValidator {
    
    public void checkAnswer (ParsedQuery parsedQuery) throws CommandProcessingException {
        if (parsedQuery.getAnswer () == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[answer]</code>");
        }
    }
    
    public void checkId (ParsedQuery parsedQuery) throws CommandProcessingException {
        if (parsedQuery.getId () == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[id]</code>");
        }
    }
    
    public void checkError (ParsedQuery parsedQuery) throws CommandProcessingException {
        if (parsedQuery.getError () != null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b> " + parsedQuery.getError ());
        }
    }
    
    public void checkTask (ParsedQuery parsedQuery) throws CommandProcessingException {
        if (parsedQuery.getTask () == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[task]</code>");
        }
    }
    
    public void checkType (ParsedQuery parsedQuery) throws CommandProcessingException {
        final var type = parsedQuery.getType ();
        
        if (type == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nОтсутствует необходимое поле <code>[type]</code>");
        } else if (!TaskContext.TYPE_TASK.equals (type) && !TaskContext.TYPE_QUESTION.equals (type)) {
            throw new CommandProcessingException (
                "<b>Ошибка в запросе:</b>\nТип задачи может быть только <code>" + TaskContext.TYPE_TASK
                + "</code> или <code>" + TaskContext.TYPE_QUESTION + "</code>"
            );
        }
    }
    
    public void checkUserGroup (UserContext user) throws CommandProcessingException {
        if (user.getGroup () == null) {
            throw new CommandProcessingException ("<b>Ошибка в запросе:</b>\nВы не можете обновить статус задачи, потому что не состоите ни в одной группе");
        }
    }
    
}
