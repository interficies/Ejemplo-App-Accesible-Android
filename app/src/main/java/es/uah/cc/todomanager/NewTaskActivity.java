package es.uah.cc.todomanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import es.uah.cc.todomanager.R;
import es.uah.cc.todomanager.domain.TaskList;

/**
 * Activity to add a new task.
 */
public class NewTaskActivity extends AppCompatActivity {
    /**
     * The activity code for request code.
     */
    public static final int ACTIVITY_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the new task 1 fragment and add it to the activity
            // using a fragment transaction.
            EditTask1Fragment fragment = EditTask1Fragment.newInstance(new OnNewTaskListener(), new TaskList.Task());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.new_task_form_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, TaskListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Listener for new task events.
     */
    protected class OnNewTaskListener implements OnEditTaskListener {

        @Override
        public void onNextStep(TaskList.Task task) {
                EditTask2Fragment fragment = EditTask2Fragment.newInstance(new OnNewTaskListener(), task);
getSupportFragmentManager().beginTransaction()
        .addToBackStack(EditTask2Fragment.TAG)
        .replace(R.id.new_task_form_container, fragment)
        .commit();
        }

        @Override
        public void onPreviousStep(TaskList.Task task) {
            getSupportFragmentManager().popBackStack();
        }

        @Override
        public void onCancel(TaskList.Task task) {
            setResult(EditTask1Fragment.TASK_CREATION_CANCELED);
            finish();
        }

        @Override
        public void onFinish(TaskList.Task task) {
            Intent intent = new Intent();
            intent.putExtra(TaskListActivity.ARG_TASK, task);
setResult(EditTask1Fragment.TASK_CREATION_COMPLETED, intent);
            finish();
        }
    }
}

